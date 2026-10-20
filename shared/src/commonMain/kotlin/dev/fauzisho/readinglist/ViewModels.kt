package dev.fauzisho.readinglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * The app's own state, above the screens: starts the database once, whichever screen the app opens on.
 * (It began in the list screen's ViewModel, until a link straight to a book showed the list was never created.)
 */
class AppViewModel(private val repo: BookRepository, private val syncer: Syncer?) : ViewModel() {
    val failure = MutableStateFlow<String?>(null)   // a database that cannot be opened is shown, not thrown
    val sync = MutableStateFlow(if (syncer == null) SyncState.Unavailable else SyncState.Never)
    val pending: StateFlow<Int> = repo.pending.catch { emit(0) }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    init {
        viewModelScope.launch { runCatching { repo.initialize() }.onFailure { failure.value = it.message ?: "the database could not be opened" } }
    }

    /** One round of sync. Offline is a state, not an error: the changes stay marked and go with the next sync. */
    fun syncNow() {
        val s = syncer ?: return
        if (sync.value == SyncState.Syncing) return
        sync.value = SyncState.Syncing
        viewModelScope.launch {
            sync.value = runCatching { s.sync() }.fold({ SyncState.Done }, { SyncState.Offline })
        }
    }
}

enum class SyncState { Unavailable, Never, Syncing, Done, Offline }

/** The list screen's state. Outlives the screen's composition, and on Android a recreated Activity. */
class BookListViewModel(private val repo: BookRepository) : ViewModel() {
    val books: StateFlow<List<Book>?> = repo.books
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    suspend fun add(title: String, author: String) = repo.add(title, author)
    fun toggle(id: Int) { viewModelScope.launch { repo.toggle(id) } }
    fun remove(id: Int) { viewModelScope.launch { repo.remove(id) } }
}

/** A value that is still loading, or has loaded, possibly as null ("there is no such book"). */
sealed interface Load<out T> {
    data object Loading : Load<Nothing>
    data class Done<T>(val value: T) : Load<T>
}

/** One book's screen. [id] comes from the route; the unsaved note from [handle]. */
class BookDetailViewModel(private val repo: BookRepository, val id: Int, handle: SavedStateHandle) : ViewModel() {
    val book: StateFlow<Load<Book?>> = repo.book(id).map { Load.Done(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Load.Loading)

    /**
     * The note being edited and not yet saved; null while it is unchanged. Kept in the SavedStateHandle, it survives
     * what a plain ViewModel field survives (the Activity being recreated) and also the process being killed.
     */
    val draft: MutableStateFlow<String?> = handle.getMutableStateFlow("draft", null)

    fun save(notes: String) { viewModelScope.launch { repo.setNotes(id, notes) } }
    fun toggle() { viewModelScope.launch { repo.toggle(id) } }
}
