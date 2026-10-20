package dev.fauzisho.readinglist

import androidx.room3.withWriteTransaction
import dev.fauzisho.readinglist.sync.HybridClock
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * The app's rules on top of the database: identical on every platform. Every edit is stamped with [clock], whose
 * state is kept in the database, so that a restart never issues a timestamp earlier than one already used.
 */
class BookRepository(
    private val db: BookDatabase,
    private val store: KeyValueStore,
    wallClock: () -> Long = { Clock.System.now().toEpochMilliseconds() },
) {
    private val dao = db.books()
    private val clock = HybridClock(wallClock)
    private var clockLoaded = false
    val books: Flow<List<Book>> = dao.observeAll()
    val pending: Flow<Int> = dao.observePending()

    private suspend fun loadClock() {
        if (!clockLoaded) { dao.setting(CLOCK)?.toLongOrNull()?.let(clock::observe); clockLoaded = true }
    }

    /** A timestamp for an edit made now. */
    suspend fun stamp(): Long {
        loadClock()
        return clock.now().also { dao.putSetting(Setting(CLOCK, it.toString())) }
    }

    /** Called by sync with every timestamp that arrives from elsewhere. */
    suspend fun observe(timestamp: Long) {
        loadClock()
        clock.observe(timestamp)
        dao.putSetting(Setting(CLOCK, clock.lastIssued.toString()))
    }

    suspend fun add(title: String, author: String): Boolean {
        val t = title.trim()
        if (t.isEmpty() || dao.countTitle(t) > 0) return false   // no duplicate titles on this device
        dao.insert(Book(title = t, author = author.trim().ifEmpty { "Unknown" }, uid = newUid(), createdAt = stamp()))
        return true
    }

    fun book(id: Int): Flow<Book?> = dao.observe(id)

    suspend fun setNotes(id: Int, notes: String) = dao.setNotes(id, notes, stamp())

    suspend fun toggle(id: Int) = dao.toggle(id, stamp())

    /** Marks the book deleted (a tombstone), so that the deletion can reach other devices. */
    suspend fun remove(id: Int) = dao.markDeleted(id, stamp())

    /**
     * Runs once per database: moves the list that version 2 kept in the key-value store into the database,
     * or starts with the sample. The "done" marker is a row in the same database, written in the same
     * transaction as the books, so the two cannot disagree: either both are there or neither is.
     */
    suspend fun initialize() = db.withWriteTransaction {
        if (dao.setting(INITIALIZED) != null) return@withWriteTransaction
        val saved = store.get(LEGACY_KEY)?.let { runCatching { Json.decodeFromString<List<Book>>(it) }.getOrNull() }
        dao.insertAll(saved?.map { it.copy(uid = newUid(), createdAt = it.id.toLong()) } ?: SAMPLE)
        dao.putSetting(Setting(INITIALIZED, "1"))
    }

    companion object {
        const val LEGACY_KEY = "books"            // where version 2 saved the list, as JSON
        const val INITIALIZED = "initialized"
        const val CLOCK = "clock"

        @OptIn(ExperimentalUuidApi::class)
        fun newUid(): String = Uuid.random().toString()

        /** Fixed uids: two devices that both start with the sample share these three books instead of doubling them. */
        val SAMPLE = listOf(
            Book(1, "An Introduction to Statistical Learning", "James, Witten, Hastie, Tibshirani", uid = "sample-1", createdAt = 1),
            Book(2, "Kotlin in Action", "Aigner, Elizarov, Isakova, Jemerov", uid = "sample-2", createdAt = 2),
            Book(3, "Designing Data-Intensive Applications", "Martin Kleppmann", read = true, uid = "sample-3", createdAt = 3, readAt = 3),
        )
    }
}
