package dev.fauzisho.readinglist

import java.io.File

/**
 * One file per key. (java.util.prefs would be the obvious choice, but it limits a value
 * to 8,192 characters, which a long reading list exceeds.)
 */
class FileStore(private val dir: File) : KeyValueStore {
    override fun get(key: String): String? = File(dir, "$key.json").takeIf { it.exists() }?.readText()
    override fun put(key: String, value: String) {
        dir.mkdirs()
        File(dir, "$key.json").writeText(value)
    }
}

/** A JVM has no system share sheet, so there is no sharer. */
fun desktopServices(): PlatformServices {
    val dir = File(System.getProperty("user.home"), ".reading-list")
    return PlatformServices(FileStore(dir), sharer = null, database = openDatabase(File(dir, "reading-list.db")),
        syncUrl = System.getenv("READING_LIST_SERVER") ?: "http://localhost:8080")
}
