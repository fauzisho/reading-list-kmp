package dev.fauzisho.readinglist

/**
 * The same stories as [SyncScenarioTest], told with the simpler designs the app avoids, as small in-memory models
 * (not the app's code): what each design ends up showing on the two devices.
 */
object NaiveSync {
    private val sample = listOf("An Introduction to Statistical Learning", "Kotlin in Action", "Designing Data-Intensive Applications")

    /** Identity = the local row number. Both devices create book 4; the server keeps whichever arrives last. */
    fun addOffline(): Map<String, Any> {
        val a = (sample + "Dune").withIndex().associate { (i, t) -> i + 1 to t }.toMutableMap()
        val b = (sample + "Neuromancer").withIndex().associate { (i, t) -> i + 1 to t }.toMutableMap()
        val server = mutableMapOf<Int, String>()
        server.putAll(a); a.putAll(server)                 // A syncs
        server.putAll(b); b.putAll(server)                 // B syncs: its book 4 replaces A's
        a.putAll(server)                                   // A syncs again
        return mapOf("A" to a.values.toList(), "B" to b.values.toList(), "design" to "a book's identity is its local row number")
    }

    /** No tombstones: a deletion removes the row, and sync takes the union of what everyone has. */
    fun delete(): Map<String, Any> {
        val server = sample.toMutableSet(); val a = sample.toMutableSet(); val b = sample.toMutableSet()
        a -= "Kotlin in Action"; server += a; a += server  // A deletes and syncs: the server still has it
        server += b; b += server; a += server              // B syncs (it still has it), then A
        return mapOf("A" to sample.filter { it in a }, "B" to sample.filter { it in b }, "design" to "deleting removes the row; sync merges by union")
    }

    /** Whole-row last-writer-wins: the row with the later "updated" time replaces the other completely. */
    fun differentFields(): Map<String, Any> {
        data class Row(val read: Boolean, val notes: String, val updatedAt: Long)
        val a = Row(read = true, notes = "", updatedAt = 1)              // A ticks it at minute 1
        val b = Row(read = false, notes = "chapter 4: coroutines", updatedAt = 2)   // B writes a note at minute 2
        val winner = if (a.updatedAt > b.updatedAt) a else b
        return mapOf("A" to mapOf("read" to winner.read, "notes" to winner.notes), "B" to mapOf("read" to winner.read, "notes" to winner.notes),
            "design" to "one timestamp per book; the later row wins whole")
    }

    /** Field-by-field, but stamped with each device's wall clock: B's clock is an hour behind. */
    fun clockSkew(): Map<String, Any> {
        val aStamp = 1L * 60                                // A writes at minute 1 (A's clock is right)
        val bStamp = 3L * 60 - 60 * 60                      // B writes at minute 3, but its clock says two hours earlier… minus 60 minutes
        val winner = if (aStamp > bStamp) "first thought" else "second thought, written later"
        return mapOf("A" to winner, "B" to winner, "design" to "timestamps from each device's wall clock")
    }

    /** Both change the same note offline: every last-writer-wins design keeps one and drops the other. */
    fun sameField(): Map<String, Any> = mapOf("A" to "B's note", "B" to "B's note", "design" to "any last-writer-wins: the earlier note is lost here too")

    /** A unique index on the title: the second "Dune" is rejected wherever the first one already is. */
    fun sameTitle(): Map<String, Any> = mapOf("A" to listOf("Herbert"), "B" to listOf("Frank Herbert"),
        "design" to "the title is unique in the schema; a synced duplicate is rejected and the devices disagree")

    /** Operations instead of state: "toggle read" is sent, applied, the answer lost, and sent again. */
    fun lostResponse(): Map<String, Any> {
        var readOnServer = false
        repeat(2) { readOnServer = !readOnServer }          // applied on the first send and again on the retry
        return mapOf("read on B" to readOnServer, "design" to "changes are sent as operations (toggle), not as state")
    }
}
