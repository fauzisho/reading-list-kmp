package dev.fauzisho.readinglist.sync

import kotlinx.serialization.Serializable

/**
 * A book as it travels between devices and the server. [uid] is the same everywhere (a local row id is not:
 * two devices offline would both create book 4). Every field that can change has the time it last changed,
 * so that two versions of the same book can be merged field by field.
 */
@Serializable
data class SyncBook(
    val uid: String,
    val title: String,
    val author: String,
    val createdAt: Long,
    val read: Boolean = false,
    val readAt: Long = 0,
    val notes: String = "",
    val notesAt: Long = 0,
    val deleted: Boolean = false,   // a tombstone: a deleted book is kept, marked, so that the deletion can travel
    val deletedAt: Long = 0,
)

/** What a device sends: its changes, and how far it has already read the server's changes. */
@Serializable
data class SyncRequest(val since: Long, val changes: List<SyncBook>)

/** What the server answers: every book that changed after [SyncRequest.since], merged, and the new position. */
@Serializable
data class SyncResponse(val cursor: Long, val changes: List<SyncBook>)
