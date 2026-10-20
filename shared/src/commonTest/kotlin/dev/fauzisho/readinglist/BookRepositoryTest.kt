package dev.fauzisho.readinglist

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/** A store the test controls. */
class MemoryStore : KeyValueStore {
    val values = mutableMapOf<String, String>()
    override fun get(key: String) = values[key]
    override fun put(key: String, value: String) { values[key] = value }
}

/** A fresh in-memory database, opened with each target's own driver. */
expect fun inMemoryDatabase(): BookDatabase

class BookRepositoryTest {
    private val db = inMemoryDatabase()
    private val store = MemoryStore()
    private val repo = BookRepository(db, store)

    @AfterTest fun close() = db.close()

    @Test fun firstStartSeedsTheSampleOnce() = runTest {
        repo.initialize()
        assertEquals(BookRepository.SAMPLE, repo.books.first())
        BookRepository.SAMPLE.forEach { repo.remove(it.id) }
        repo.initialize()                               // a second start must not bring the sample back
        assertEquals(emptyList(), repo.books.first())
    }

    @Test fun importsTheListVersion2Saved() = runTest {
        val v2 = listOf(Book(4, "Effective Kotlin", "Moskała", read = true), Book(9, "Saved in version 2", "Unknown"))
        store.values[BookRepository.LEGACY_KEY] = Json.encodeToString(v2)
        repo.initialize()
        val imported = repo.books.first()
        assertEquals(v2.map { Triple(it.id, it.title, it.read) }, imported.map { Triple(it.id, it.title, it.read) })
        assertEquals(2, imported.map { it.uid }.toSet().size)   // each imported book got its own uid
    }

    @Test fun losingTheKeyValueStoreDoesNotImportAgain() = runTest {
        // Found by the browser test of an earlier version, which kept the "imported" flag in the key-value store:
        // with that store cleared and the database kept, the next start imported a second time.
        store.values[BookRepository.LEGACY_KEY] = Json.encodeToString(listOf(Book(9, "Saved in version 2", "Unknown")))
        repo.initialize()
        repo.remove(9)
        store.values.clear()
        store.values[BookRepository.LEGACY_KEY] = Json.encodeToString(listOf(Book(9, "Saved in version 2", "Unknown")))
        repo.initialize()
        assertEquals(emptyList(), repo.books.first())
    }

    @Test fun unreadableSavedDataFallsBackToTheSample() = runTest {
        store.values[BookRepository.LEGACY_KEY] = "{not json"
        repo.initialize()
        assertEquals(BookRepository.SAMPLE, repo.books.first())
    }

    @Test fun theSchemaRejectsDuplicateTitlesInAnyCase() = runTest {
        repo.initialize()
        assertFalse(repo.add("kotlin IN action", "someone"))
        assertFalse(repo.add("   ", "someone"))
        assertTrue(repo.add("  Effective Kotlin ", ""))
        val added = repo.books.first().last()
        assertEquals("Effective Kotlin" to "Unknown", added.title to added.author)
        assertTrue(added.id > 3)   // a new id, but not necessarily 4: the ignored duplicate may have used one up
    }

    @Test fun theFlowEmitsAfterEveryChange() = runTest {
        repo.initialize()
        repo.toggle(1)
        assertTrue(repo.books.first().first { it.id == 1 }.read)
        repo.remove(2)
        assertEquals(listOf(1, 3), repo.books.first().map { it.id })
    }

    @Test fun notesAreSavedAndObserved() = runTest {
        repo.initialize()
        assertEquals("", repo.book(2).first()?.notes)
        repo.setNotes(2, "chapter 4 on coroutines")
        assertEquals("chapter 4 on coroutines", repo.book(2).first()?.notes)
        assertEquals(null, repo.book(99).first())
    }
}
