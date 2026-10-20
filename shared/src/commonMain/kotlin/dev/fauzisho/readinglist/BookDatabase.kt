package dev.fauzisho.readinglist

import androidx.room3.AutoMigration
import androidx.room3.ColumnInfo
import androidx.room3.ConstructedBy
import androidx.room3.Dao
import androidx.room3.Database
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.PrimaryKey
import androidx.room3.Query
import androidx.room3.RoomDatabase
import androidx.room3.RoomDatabaseConstructor
import androidx.room3.Update
import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.async.executeSQL
import dev.fauzisho.readinglist.sync.SyncBook
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

/**
 * One row per book. [id] is this device's row number, used for navigation; [uid] is the book's identity on every
 * device. Each field that can change carries the time it last changed, and a deleted book stays as a tombstone
 * until the deletion has reached the server. [dirty]: changed here and not yet sent.
 */
@Serializable
@Entity(indices = [Index(value = ["uid"], unique = true)])
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val title: String,
    val author: String,
    val read: Boolean = false,
    @ColumnInfo(defaultValue = "") val notes: String = "",        // new in schema version 2
    @ColumnInfo(defaultValue = "") val uid: String = "",          // new in schema version 3, and everything below
    @ColumnInfo(defaultValue = "0") val createdAt: Long = 0,
    @ColumnInfo(defaultValue = "0") val readAt: Long = 0,
    @ColumnInfo(defaultValue = "0") val notesAt: Long = 0,
    @ColumnInfo(defaultValue = "0") val deleted: Boolean = false,
    @ColumnInfo(defaultValue = "0") val deletedAt: Long = 0,
    @ColumnInfo(defaultValue = "1") val dirty: Boolean = true,
)

fun Book.toSync() = SyncBook(uid, title, author, createdAt, read, readAt, notes, notesAt, deleted, deletedAt)

/** This row with the synced values of [s]; the local row number stays. */
fun Book.with(s: SyncBook, dirty: Boolean) = copy(title = s.title, author = s.author, createdAt = s.createdAt, read = s.read, readAt = s.readAt,
    notes = s.notes, notesAt = s.notesAt, deleted = s.deleted, deletedAt = s.deletedAt, dirty = dirty)

fun SyncBook.toBook(dirty: Boolean) = Book(title = title, author = author, read = read, notes = notes, uid = uid, createdAt = createdAt,
    readAt = readAt, notesAt = notesAt, deleted = deleted, deletedAt = deletedAt, dirty = dirty)

/** Small facts about the database itself, kept in the same file as the data they describe. */
@Entity
data class Setting(@PrimaryKey val name: String, val value: String)

@Dao
interface BookDao {
    /** Emits the list again after every change to the table. Deleted books are tombstones, not shown. */
    @Query("SELECT * FROM Book WHERE deleted = 0 ORDER BY createdAt, id")
    fun observeAll(): Flow<List<Book>>

    @Query("SELECT * FROM Book WHERE id = :id AND deleted = 0")
    fun observe(id: Int): Flow<Book?>

    /** Returns the new row id, or -1 when the uid already exists. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(book: Book): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(books: List<Book>)

    @Update
    suspend fun update(book: Book)

    @Query("SELECT COUNT(*) FROM Book WHERE title = :title AND deleted = 0")   // the column compares without case
    suspend fun countTitle(title: String): Int

    @Query("UPDATE Book SET notes = :notes, notesAt = :at, dirty = 1 WHERE id = :id")
    suspend fun setNotes(id: Int, notes: String, at: Long)

    @Query("UPDATE Book SET read = NOT read, readAt = :at, dirty = 1 WHERE id = :id")
    suspend fun toggle(id: Int, at: Long)

    @Query("UPDATE Book SET deleted = 1, deletedAt = :at, dirty = 1 WHERE id = :id")
    suspend fun markDeleted(id: Int, at: Long)

    @Query("SELECT COUNT(*) FROM Book WHERE deleted = 0")
    suspend fun count(): Int

    // ---- sync
    @Query("SELECT * FROM Book WHERE dirty = 1")
    suspend fun dirty(): List<Book>

    @Query("SELECT COUNT(*) FROM Book WHERE dirty = 1")
    fun observePending(): Flow<Int>

    @Query("SELECT * FROM Book WHERE uid = :uid")
    suspend fun byUid(uid: String): Book?

    /** Sent and unchanged since: no longer dirty. A row edited while the request was on its way stays dirty. */
    @Query("UPDATE Book SET dirty = 0 WHERE uid = :uid AND readAt = :readAt AND notesAt = :notesAt AND deletedAt = :deletedAt")
    suspend fun markSent(uid: String, readAt: Long, notesAt: Long, deletedAt: Long)

    @Query("SELECT value FROM Setting WHERE name = :name")
    suspend fun setting(name: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun putSetting(setting: Setting)

    /** Which SQLite this platform's driver runs: it differs between platforms, and on Android between devices. */
    @Query("SELECT sqlite_version()")
    suspend fun sqliteVersion(): String
}

/**
 * Schema 2 → 3, written by hand: a new column can get a constant default, but every existing book needs its own
 * uid, and that needs SQL. The three sample books get fixed uids, so that two devices that both started with the
 * sample end up with one copy of each after their first sync, not two. The title's unique index goes: two devices
 * may add the same title while offline, and a sync must be able to store both.
 */
val MIGRATION_2_3 = object : Migration(2, 3) {
    override suspend fun migrate(connection: SQLiteConnection) {
        for (column in listOf("uid TEXT NOT NULL DEFAULT ''", "createdAt INTEGER NOT NULL DEFAULT 0", "readAt INTEGER NOT NULL DEFAULT 0",
            "notesAt INTEGER NOT NULL DEFAULT 0", "deleted INTEGER NOT NULL DEFAULT 0", "deletedAt INTEGER NOT NULL DEFAULT 0",
            "dirty INTEGER NOT NULL DEFAULT 1")) {
            connection.executeSQL("ALTER TABLE `Book` ADD COLUMN `${column.substringBefore(' ')}` ${column.substringAfter(' ')}")
        }
        connection.executeSQL("UPDATE `Book` SET uid = lower(hex(randomblob(16))), createdAt = id")
        BookRepository.SAMPLE.forEach { b ->
            connection.executeSQL("UPDATE `Book` SET uid = '${b.uid}' WHERE id = ${b.id} AND title = '${b.title.replace("'", "''")}'")
        }
        connection.executeSQL("DROP INDEX IF EXISTS `index_Book_title`")
        connection.executeSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_Book_uid` ON `Book` (`uid`)")
    }
}

// Version 2 added Book.notes (Room wrote that migration); version 3 adds what sync needs (written by hand, above).
@Database(entities = [Book::class, Setting::class], version = 3, autoMigrations = [AutoMigration(from = 1, to = 2)])
@ConstructedBy(BookDatabaseConstructor::class)
abstract class BookDatabase : RoomDatabase() {
    abstract fun books(): BookDao
}

// The one expect in this project that nobody writes an actual for: Room's KSP processor generates it for each target.
@Suppress("KotlinNoActualForExpect")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<BookDatabase> {
    override fun initialize(): BookDatabase
}
