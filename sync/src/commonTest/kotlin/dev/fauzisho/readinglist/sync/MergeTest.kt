package dev.fauzisho.readinglist.sync

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MergeTest {
    private val base = SyncBook("u1", "Dune", "Herbert", createdAt = 100)

    @Test fun differentFieldsChangedOnTwoDevicesAreBothKept() {
        val a = base.copy(read = true, readAt = 200)
        val b = base.copy(notes = "worldbuilding", notesAt = 210)
        val m = merge(a, b)
        assertTrue(m.read); assertEquals("worldbuilding", m.notes)
    }

    @Test fun theSameFieldTakesTheLaterEdit() {
        assertEquals("second", merge(base.copy(notes = "first", notesAt = 200), base.copy(notes = "second", notesAt = 300)).notes)
    }

    @Test fun aDeletionWinsOverLaterEdits() {
        val deleted = base.copy(deleted = true, deletedAt = 200)
        val edited = base.copy(notes = "still reading", notesAt = 900)
        assertTrue(merge(deleted, edited).deleted)
    }

    @Test fun orderAndRepetitionDoNotMatter() {
        val versions = listOf(base.copy(read = true, readAt = 5), base.copy(notes = "n", notesAt = 7),
            base.copy(notes = "m", notesAt = 7), base.copy(deleted = true, deletedAt = 3))
        val forward = versions.reduce(::merge)
        val backward = versions.reversed().reduce(::merge)
        assertEquals(forward, backward)                       // commutative and associative here
        assertEquals(forward, merge(forward, versions[1]))    // idempotent
    }

    @Test fun theClockNeverGoesBehindWhatItHasSeen() {
        var wall = 1_000L
        val clock = HybridClock({ wall })
        clock.observe(5_000)                // a change from a device whose clock is ahead
        assertTrue(clock.now() > 5_000)     // the next local edit is still later
        wall = 9_000
        assertEquals(9_000, clock.now())    // and the wall clock takes over again once it catches up
    }

    @Test fun theServerReturnsOnlyWhatChangedSinceTheCursor() {
        val server = ServerStore()
        val first = server.sync(SyncRequest(0, listOf(base)))
        assertEquals(listOf(base), first.changes)
        val again = server.sync(SyncRequest(first.cursor, emptyList()))
        assertEquals(emptyList(), again.changes)                         // nothing new
        val same = server.sync(SyncRequest(first.cursor, listOf(base)))  // resending changes nothing
        assertEquals(first.cursor, same.cursor)
    }
}
