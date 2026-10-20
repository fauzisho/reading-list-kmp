package dev.fauzisho.readinglist

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

/** The app's screens. Serializable, so a back stack of them can be saved and restored. */
@Serializable
sealed interface Route : NavKey

@Serializable
data object BookList : Route

@Serializable
data class BookDetail(val id: Int) : Route

/** Off the JVM there is no reflection to find a NavKey's serializer, so the routes are registered by hand. */
val routeConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(BookList::class, BookList.serializer())
            subclass(BookDetail::class, BookDetail.serializer())
        }
    }
}

/** A route as it appears in a browser's address bar, and back. */
fun Route.toPath(): String = when (this) {
    BookList -> "#/"
    is BookDetail -> "#/book/$id"
}

fun routeFromPath(path: String): Route? {
    val p = path.removePrefix("#").trimEnd('/')
    if (p.isEmpty()) return BookList
    val id = Regex("/book/(\\d+)").matchEntire(p)?.groupValues?.get(1)?.toIntOrNull() ?: return null
    return BookDetail(id)
}

/** The back stack that a route implies when someone arrives at it directly (a reload, a link). */
fun stackFor(route: Route?): List<Route> = when (route) {
    null, BookList -> listOf(BookList)
    is BookDetail -> listOf(BookList, route)
}

/** The browser's history: its address bar and its back and forward buttons. Null on every other platform. */
interface BrowserHistory {
    /** The route in the address bar when the page was opened. */
    val initial: Route?
    fun push(route: Route)
    /** Changes the current entry instead of adding one. */
    fun replace(route: Route)
    fun back()
    /** Calls [onChange] when the user moves through history (back, forward); returns a function that stops it. */
    fun listen(onChange: (Route?) -> Unit): () -> Unit
}

/**
 * Moves between screens. On Android, desktop and iOS the back stack is the only truth.
 * In a browser, history is: going back asks the browser to go back, and the back stack follows the address bar.
 */
class Navigator(private val backStack: MutableList<NavKey>, private val history: BrowserHistory?) {
    fun open(route: Route) {
        // With the list and a book side by side, choosing another book replaces the book: back still goes to the list.
        if (route is BookDetail && backStack.lastOrNull() is BookDetail) {
            backStack[backStack.lastIndex] = route
            history?.replace(route)
        } else {
            backStack.add(route)
            history?.push(route)
        }
    }

    fun back() {
        if (history != null) history.back()
        else if (backStack.size > 1) backStack.removeAt(backStack.lastIndex)
    }

    /** The browser moved (back, forward): rebuild the stack from the address bar. */
    fun follow(route: Route?) {
        val target = stackFor(route)
        if (backStack.toList() == target) return
        backStack.clear()
        backStack.addAll(target)
    }
}
