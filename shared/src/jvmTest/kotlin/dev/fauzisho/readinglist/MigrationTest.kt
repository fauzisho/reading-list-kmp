package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.room3.testing.MigrationTestHelper
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import androidx.sqlite.execSQL
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/** A database written by version 3 of the app (schema 1) must open in version 4 (schema 2) with every book intact. */
class MigrationTest {
    private val file: Path = Files.createTempDirectory("migration").resolve("reading-list.db")

    @get:Rule
    val helper = MigrationTestHelper(
        schemaDirectoryPath = Path.of("schemas"),
        databasePath = file,
        driver = BundledSQLiteDriver(),
        databaseClass = BookDatabase::class,
    )

    @Test fun schema1To2To3KeepsTheBooksAndGivesEachAUid() = runTest {
        helper.createDatabase(1).apply {
            execSQL("INSERT INTO Book (id, title, author, read) VALUES (2, 'Kotlin in Action', 'Aigner, Elizarov, Isakova, Jemerov', 0)")
            execSQL("INSERT INTO Book (id, title, author, read) VALUES (7, 'Effective Kotlin', 'Moskała', 1)")
            execSQL("INSERT INTO Setting (name, value) VALUES ('initialized', '1')")
            close()
        }
        helper.runMigrationsAndValidate(2).close()                  // Room's auto-migration, validated against 2.json
        helper.runMigrationsAndValidate(3, listOf(MIGRATION_2_3)).close()   // the hand-written one, against 3.json

        val db = Room.databaseBuilder<BookDatabase>(file.toString()).setDriver(BundledSQLiteDriver()).addMigrations(MIGRATION_2_3).build()
        val books = db.books().observeAll().first()
        assertEquals(listOf("Kotlin in Action", "Effective Kotlin"), books.map { it.title })
        assertEquals("sample-2", books[0].uid)                      // a sample book keeps the uid every device gives it
        assertEquals(32, books[1].uid.length)                       // any other book gets a random one
        assertTrue(books.all { it.dirty && !it.deleted && it.notes == "" })
        BookRepository(db, MemoryStore()).initialize()              // the marker came through: no sample is added
        assertEquals(2, db.books().count())
        db.close()
    }
}
