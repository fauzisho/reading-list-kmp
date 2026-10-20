package dev.fauzisho.readinglist

/** A string key-value store: SharedPreferences, NSUserDefaults, a file, or localStorage. */
interface KeyValueStore {
    fun get(key: String): String?
    fun put(key: String, value: String)
}

/** Hands text to the platform's share sheet. */
fun interface Sharer {
    fun share(text: String)
}

/**
 * What the shared code needs from the platform, built once by each entry point.
 * [sharer] is null where the platform has no share sheet, so the UI can leave the button out.
 * [database] is opened by the platform, because only the platform knows its SQLite driver and where files go.
 * [storageWarning] says why, when the platform could only open a database that will not be kept.
 * [history] is the browser's history on the web, and null everywhere else.
 * [syncUrl] is the sync server's address, or null where there is none (then the app has no Sync button).
 */
class PlatformServices(
    val store: KeyValueStore,
    val sharer: Sharer?,
    val database: BookDatabase,
    val storageWarning: StorageWarning? = null,
    val history: BrowserHistory? = null,
    val syncUrl: String? = null,
)

/** Why the list will not be kept. The words are in the app's string resources, in the user's language. */
enum class StorageWarning { OtherTab, NoStorage }
