@file:OptIn(ExperimentalWasmJsInterop::class)   // js("…") bodies: Kotlin/Wasm's JavaScript interop

package dev.fauzisho.readinglist

// localStorage can throw (storage disabled, private windows), so both calls are guarded.
private fun storageGet(key: String): String? = js("{ try { return localStorage.getItem(key); } catch (e) { return null; } }")
private fun storageSet(key: String, value: String): Unit = js("{ try { localStorage.setItem(key, value); } catch (e) {} }")
// A server only when the page is opened with ?server=…: the copy embedded in the blog has none, and no Sync button
private fun serverParameter(): String? = js("new URLSearchParams(window.location.search).get('server')")
private fun canShare(): Boolean = js("typeof navigator.share === 'function'")
private fun webShare(text: String): Unit = js("{ navigator.share({ text: text }).catch(function () {}); }")

class LocalStorageStore : KeyValueStore {
    override fun get(key: String): String? = storageGet(key)
    override fun put(key: String, value: String) = storageSet(key, value)
}

/** The Web Share API exists in some browsers and not others: decided at run time, not at compile time. */
suspend fun webServices(): PlatformServices {
    val (database, warning) = openDatabase()
    return PlatformServices(LocalStorageStore(), if (canShare()) Sharer(::webShare) else null, database, warning, WebHistory(), serverParameter())
}
