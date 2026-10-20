@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.fauzisho.readinglist

private fun currentHash(): String = js("window.location.hash")
private fun pushHash(hash: String): Unit = js("window.history.pushState(null, '', hash)")
private fun replaceHash(hash: String): Unit = js("window.history.replaceState(null, '', hash)")
private fun goBack(): Unit = js("window.history.back()")
private fun onPopState(callback: () -> Unit): JsAny = js("{ const f = () => callback(); window.addEventListener('popstate', f); return f; }")
private fun offPopState(listener: JsAny): Unit = js("window.removeEventListener('popstate', listener)")

/** The address bar holds the current screen (#/book/2), so a reload, a link and the browser's buttons all work. */
class WebHistory : BrowserHistory {
    override val initial: Route? = routeFromPath(currentHash())
    override fun push(route: Route) = pushHash(route.toPath())
    override fun replace(route: Route) = replaceHash(route.toPath())
    override fun back() = goBack()
    override fun listen(onChange: (Route?) -> Unit): () -> Unit {
        val listener = onPopState { onChange(routeFromPath(currentHash())) }
        return { offPopState(listener) }
    }
}
