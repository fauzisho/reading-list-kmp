package dev.fauzisho.readinglist.apple

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

// The shapes the reading list's shared code uses, in one place, for an iOS app to call.

enum class SyncState { Unavailable, Never, Syncing, Done, Offline }

sealed interface Route {
    data object BookList : Route
    data class BookDetail(val id: Int) : Route
}

sealed interface Load<out T> {
    data object Loading : Load<Nothing>
    data class Done<T>(val value: T) : Load<T>
}

data class Book(val id: Int, val title: String, val author: String, val read: Boolean = false, val notes: String = "")

class Library {
    private val state = MutableStateFlow(listOf(Book(1, "Kotlin in Action", "Aigner, Elizarov, Isakova, Jemerov")))

    val changes: Flow<List<Book>> get() = state

    fun books(): List<Book> = state.value

    fun find(id: Int): Book? = state.value.firstOrNull { it.id == id }

    fun load(id: Int): Load<Book> = find(id)?.let { Load.Done(it) } ?: Load.Loading

    fun count(): Long = state.value.size.toLong()

    suspend fun add(title: String, author: String = "Unknown"): Boolean {
        if (state.value.any { it.title == title }) return false
        state.value = state.value + Book(state.value.size + 1, title, author)
        return true
    }
}

/** The later of two edits wins, field by field (a simplified version of the sync module's merge). */
fun merge(a: Book, b: Book): Book = if (b.notes.isNotEmpty()) b else a.copy(read = a.read || b.read)

fun readCount(books: List<Book>, includeUnread: Boolean = false): Int = books.count { it.read || includeUnread }
