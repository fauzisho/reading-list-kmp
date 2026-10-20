package dev.fauzisho.readinglist

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.TimeSource

/**
 * Not a check of correctness: times the same work on each target's driver and prints it.
 * Each round uses a fresh in-memory database; round 0 warms up, and the median of rounds 1–5 is printed.
 */
class DatabaseTimingTest {
    private suspend fun ms(block: suspend () -> Unit): Double {
        val t = TimeSource.Monotonic.markNow()
        block()
        return t.elapsedNow().inWholeMicroseconds / 1000.0
    }

    @Test fun timeTheSameWorkEverywhere() = runTest {
        val rounds = (0..5).map {
            val db = inMemoryDatabase()
            val dao = db.books()
            val single = ms { repeat(200) { dao.insert(Book(title = "single $it", author = "a", uid = "s$it")) } }       // 200 transactions
            val batch = ms { dao.insertAll(List(1000) { Book(title = "batch $it", author = "a", uid = "b$it") }) }        // 1 transaction
            val read = ms { repeat(50) { dao.observeAll().first() } }                                        // 50 reads of 1,200 rows
            assertEquals(1200, dao.count())
            val version = dao.sqliteVersion()
            db.close()
            listOf(single, batch, read) to version
        }.drop(1)
        fun median(i: Int) = rounds.map { it.first[i] }.sorted()[rounds.size / 2]
        println("TIMING sqlite=${rounds[0].second} single200=${median(0)} batch1000=${median(1)} read50=${median(2)}")
    }
}
