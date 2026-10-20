package dev.fauzisho.readinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.fauzisho.readinglist.resources.Res
import dev.fauzisho.readinglist.resources.action_add
import dev.fauzisho.readinglist.resources.action_remove
import dev.fauzisho.readinglist.resources.action_save_notes
import dev.fauzisho.readinglist.resources.action_share
import dev.fauzisho.readinglist.resources.all_books
import dev.fauzisho.readinglist.resources.app_title
import dev.fauzisho.readinglist.resources.choose_a_book
import dev.fauzisho.readinglist.resources.field_author
import dev.fauzisho.readinglist.resources.field_notes
import dev.fauzisho.readinglist.resources.field_title
import dev.fauzisho.readinglist.resources.has_notes
import dev.fauzisho.readinglist.resources.load_failed
import dev.fauzisho.readinglist.resources.loading
import dev.fauzisho.readinglist.resources.not_in_list
import dev.fauzisho.readinglist.resources.opening_database
import dev.fauzisho.readinglist.resources.read_count
import dev.fauzisho.readinglist.resources.running_on
import dev.fauzisho.readinglist.resources.saved
import dev.fauzisho.readinglist.resources.sync_action
import dev.fauzisho.readinglist.resources.sync_done
import dev.fauzisho.readinglist.resources.sync_never
import dev.fauzisho.readinglist.resources.sync_offline
import dev.fauzisho.readinglist.resources.sync_pending
import dev.fauzisho.readinglist.resources.sync_running
import dev.fauzisho.readinglist.resources.state_read
import dev.fauzisho.readinglist.resources.state_unread
import dev.fauzisho.readinglist.resources.unsaved
import dev.fauzisho.readinglist.resources.warning_no_storage
import dev.fauzisho.readinglist.resources.warning_other_tab
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/** The whole UI, written once in commonMain: two screens, shown one at a time or side by side. */
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App(services: PlatformServices) {
    val repo = remember { BookRepository(services.database, services.store) }
    // >>> back stack
    val backStack = rememberNavBackStack(routeConfig, *stackFor(services.history?.initial).toTypedArray())
    // <<< back stack
    val navigator = remember(backStack) { Navigator(backStack, services.history) }
    val app = viewModel { AppViewModel(repo, services.syncUrl?.let { Syncer(services.database, repo, HttpSyncApi(it)) }) }
    val failure by app.failure.collectAsState()
    services.history?.let { history -> DisposableEffect(history) { onDispose(history.listen(navigator::follow)) } }

    // On a wide window the list and the open book share the screen; on a narrow one, one at a time
    val listDetail = rememberListDetailSceneStrategy<NavKey>()
    val selected = (backStack.lastOrNull() as? BookDetail)?.id
    // the same decision the strategy makes: how many panes fit side by side in this window
    val sideBySide = calculatePaneScaffoldDirective(currentWindowAdaptiveInfo()).maxHorizontalPartitions > 1

    MaterialTheme(colorScheme = appColorScheme()) {
        Surface(Modifier.fillMaxSize()) {
            NavDisplay(
                backStack = backStack,
                // keep clear of the status and navigation bars (Android draws edge to edge) and of iOS's safe area;
                // zero on the desktop and the web
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                sceneStrategies = listOf(listDetail),
                onBack = { navigator.back() },   // Android's back gesture, and the screens' own back buttons
                entryDecorators = listOf(
                    rememberSaveableStateHolderNavEntryDecorator(),   // rememberSaveable state per screen
                    rememberViewModelStoreNavEntryDecorator(),        // a ViewModel per screen, cleared when it is popped
                ),
                entryProvider = entryProvider {
                    entry<BookList>(metadata = ListDetailSceneStrategy.listPane(detailPlaceholder = { ChooseABook() })) {
                        BookListScreen(viewModel { BookListViewModel(repo) }, services, failure, app, selected, onOpen = { navigator.open(BookDetail(it)) })
                    }
                    entry<BookDetail>(metadata = ListDetailSceneStrategy.detailPane()) { route ->
                        BookDetailScreen(viewModel { BookDetailViewModel(repo, route.id, createSavedStateHandle()) }, onBack = navigator::back.takeUnless { sideBySide })
                    }
                },
            )
        }
    }
}

@Composable
private fun BookListScreen(vm: BookListViewModel, services: PlatformServices, failure: String?, app: AppViewModel, selected: Int?, onOpen: (Int) -> Unit) {
    val books by vm.books.collectAsState()
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(stringResource(Res.string.app_title), style = MaterialTheme.typography.headlineSmall)
        val list = books
        val runningOn = stringResource(Res.string.running_on, platformName())
        Text(
            if (list == null) stringResource(Res.string.opening_database) + " · " + runningOn
            else pluralStringResource(Res.plurals.read_count, list.size, list.count { it.read }, list.size) + " · " + runningOn,
            style = MaterialTheme.typography.bodySmall,
        )
        val warning = when (services.storageWarning) {
            StorageWarning.OtherTab -> stringResource(Res.string.warning_other_tab)
            StorageWarning.NoStorage -> stringResource(Res.string.warning_no_storage)
            null -> null
        }
        (failure?.let { stringResource(Res.string.load_failed, it) } ?: warning)?.let {
            Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
        }
        SyncRow(app)
        AddBook(onAdd = vm::add)
        LazyColumn(Modifier.fillMaxWidth()) {
            items(list.orEmpty(), key = { it.id }) { book ->
                val rowColor = if (book.id == selected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
                Row(Modifier.background(rowColor), verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = book.read, onCheckedChange = { vm.toggle(book.id) })
                    Column(Modifier.weight(1f).clickable { onOpen(book.id) }.padding(vertical = 6.dp)) {
                        Text(book.title, textDecoration = if (book.read) TextDecoration.LineThrough else null)
                        val notes = if (book.notes.isNotBlank()) " · " + stringResource(Res.string.has_notes) else ""
                        Text(book.author + notes, style = MaterialTheme.typography.bodySmall)
                    }
                    services.sharer?.let { sharer ->   // no share sheet on this platform: no button
                        TextButton(onClick = { sharer.share("${book.title} · ${book.author}") }) { Text(stringResource(Res.string.action_share)) }
                    }
                    TextButton(onClick = { vm.remove(book.id) }) { Text(stringResource(Res.string.action_remove)) }
                }
            }
        }
    }
}

@Composable
private fun BookDetailScreen(vm: BookDetailViewModel, onBack: (() -> Unit)?) {
    val book by vm.book.collectAsState()
    // >>> draft holder
    val draftFlow = vm.draft
    val draft by draftFlow.collectAsState()
    fun setDraft(text: String?) { draftFlow.value = text }
    // <<< draft holder
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        onBack?.let { TextButton(onClick = it) { Text(stringResource(Res.string.all_books)) } }   // no back button beside the list
        val b = when (val state = book) {
            Load.Loading -> return@Column Text(stringResource(Res.string.loading))
            is Load.Done -> state.value ?: return@Column Text(stringResource(Res.string.not_in_list))   // a stale link
        }
        Text(b.title, style = MaterialTheme.typography.headlineSmall)
        Text(b.author, style = MaterialTheme.typography.bodyMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = b.read, onCheckedChange = { vm.toggle() })
            Text(stringResource(if (b.read) Res.string.state_read else Res.string.state_unread))
        }
        OutlinedTextField(
            value = draft ?: b.notes,
            onValueChange = { setDraft(it) },
            label = { Text(stringResource(Res.string.field_notes)) },
            minLines = 4,
            modifier = Modifier.fillMaxWidth(),
        )
        val unsaved = draft != null && draft != b.notes
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { draft?.let(vm::save); setDraft(null) }, enabled = unsaved) { Text(stringResource(Res.string.action_save_notes)) }
            Spacer(Modifier.width(12.dp))
            Text(stringResource(if (unsaved) Res.string.unsaved else Res.string.saved), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun AddBook(onAdd: suspend (String, String) -> Boolean) {
    val scope = rememberCoroutineScope()
    var title by rememberSaveable { mutableStateOf("") }
    var author by rememberSaveable { mutableStateOf("") }
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(title, { title = it }, label = { Text(stringResource(Res.string.field_title)) }, singleLine = true, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        OutlinedTextField(author, { author = it }, label = { Text(stringResource(Res.string.field_author)) }, singleLine = true, modifier = Modifier.weight(1f))
        Spacer(Modifier.width(8.dp))
        Button(onClick = { scope.launch { if (onAdd(title, author)) { title = ""; author = "" } } }) { Text(stringResource(Res.string.action_add)) }
    }
}

@Composable
private fun ChooseABook() {
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
        Text(stringResource(Res.string.choose_a_book), style = MaterialTheme.typography.bodyLarge)
    }
}

/** Shown only where there is a server: the Sync button, and where things stand. */
@Composable
private fun SyncRow(app: AppViewModel) {
    val state by app.sync.collectAsState()
    if (state == SyncState.Unavailable) return
    val pending by app.pending.collectAsState()
    Row(verticalAlignment = Alignment.CenterVertically) {
        Button(onClick = app::syncNow, enabled = state != SyncState.Syncing) { Text(stringResource(Res.string.sync_action)) }
        Spacer(Modifier.width(12.dp))
        val status = stringResource(when (state) {
            SyncState.Never, SyncState.Unavailable -> Res.string.sync_never
            SyncState.Syncing -> Res.string.sync_running
            SyncState.Done -> Res.string.sync_done
            SyncState.Offline -> Res.string.sync_offline
        })
        val waiting = if (pending > 0) " · " + pluralStringResource(Res.plurals.sync_pending, pending, pending) else ""
        Text(status + waiting, style = MaterialTheme.typography.bodySmall)
    }
}
