package dev.fauzisho.readinglist

import androidx.navigation3.runtime.NavKey
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/** A browser history the test controls: back() only records the request, as the real one answers later with popstate. */
class FakeHistory(override val initial: Route? = null) : BrowserHistory {
    val pushed = mutableListOf<Route>()
    val replaced = mutableListOf<Route>()
    var backs = 0
    override fun push(route: Route) { pushed += route }
    override fun replace(route: Route) { replaced += route }
    override fun back() { backs++ }
    override fun listen(onChange: (Route?) -> Unit): () -> Unit = {}
}

class NavigationTest {
    @Test fun routesRoundTripThroughTheAddressBar() {
        for (r in listOf(BookList, BookDetail(2), BookDetail(41))) assertEquals(r, routeFromPath(r.toPath()))
        assertEquals(BookList, routeFromPath(""))
        assertEquals(BookList, routeFromPath("#/"))
        assertNull(routeFromPath("#/book/two"))
        assertNull(routeFromPath("#/settings"))
    }

    @Test fun arrivingAtABookPutsTheListUnderIt() {
        assertEquals(listOf(BookList, BookDetail(2)), stackFor(BookDetail(2)))
        assertEquals(listOf<Route>(BookList), stackFor(null))   // an unknown address falls back to the list
    }

    @Test fun withoutABrowserTheBackStackIsTheTruth() {
        val stack = mutableListOf<NavKey>(BookList)
        val nav = Navigator(stack, history = null)
        nav.open(BookDetail(3))
        assertEquals(listOf<NavKey>(BookList, BookDetail(3)), stack)
        nav.back(); nav.back()                                  // the list is never popped
        assertEquals(listOf<NavKey>(BookList), stack)
    }

    @Test fun inABrowserTheAddressBarIsTheTruth() {
        val stack = mutableListOf<NavKey>(BookList)
        val history = FakeHistory()
        val nav = Navigator(stack, history)
        nav.open(BookDetail(3))
        assertEquals(listOf<Route>(BookDetail(3)), history.pushed)
        nav.back()                                              // asks the browser; the stack waits for popstate
        assertEquals(1, history.backs)
        assertEquals(listOf<NavKey>(BookList, BookDetail(3)), stack)
        nav.follow(BookList)                                    // popstate arrives with the address "#/"
        assertEquals(listOf<NavKey>(BookList), stack)
        nav.follow(BookDetail(3))                               // the forward button
        assertEquals(listOf<NavKey>(BookList, BookDetail(3)), stack)
    }

    @Test fun choosingAnotherBookReplacesTheBook() {
        val stack = mutableListOf<NavKey>(BookList)
        val history = FakeHistory()
        val nav = Navigator(stack, history)
        nav.open(BookDetail(1)); nav.open(BookDetail(2)); nav.open(BookDetail(3))
        assertEquals(listOf<NavKey>(BookList, BookDetail(3)), stack)   // one back press returns to the list
        assertEquals(listOf<Route>(BookDetail(1)), history.pushed)
        assertEquals(listOf<Route>(BookDetail(2), BookDetail(3)), history.replaced)
    }
}
