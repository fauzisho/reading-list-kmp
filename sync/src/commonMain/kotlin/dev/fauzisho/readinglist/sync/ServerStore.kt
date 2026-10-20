package dev.fauzisho.readinglist.sync

/**
 * The server's side of sync, kept free of any web framework so that it can be tested on every target: books by
 * uid, each with the sequence number of its last change. A device sends its changes and the last sequence number
 * it has seen; it gets back everything that changed after that, merged, including its own changes.
 */
class ServerStore {
    private val books = mutableMapOf<String, Pair<SyncBook, Long>>()
    private var sequence = 0L

    fun sync(request: SyncRequest): SyncResponse {
        for (incoming in request.changes) {
            val current = books[incoming.uid]?.first
            val merged = if (current == null) incoming else merge(current, incoming)
            if (merged != current) books[incoming.uid] = merged to ++sequence
        }
        val changed = books.values.filter { it.second > request.since }.sortedBy { it.second }.map { it.first }
        return SyncResponse(cursor = sequence, changes = changed)
    }

    fun all(): List<SyncBook> = books.values.map { it.first }.sortedBy { it.createdAt }
}
