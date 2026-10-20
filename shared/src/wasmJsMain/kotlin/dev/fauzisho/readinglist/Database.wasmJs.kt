@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import kotlinx.coroutines.await
import org.w3c.dom.Worker
import kotlin.js.Promise

// Webpack sees this literal `new Worker(new URL(…))` and bundles the worker, with SQLite's WebAssembly build, as its own file.
private fun sqliteWorker(): Worker = js("""new Worker(new URL("reading-list-sqlite-worker/worker.js", import.meta.url))""")

// The OPFS files can be open in one tab at a time. A Web Lock, held until the page closes, decides which tab that is.
// Resolves to "owner", "busy" (another tab has it) or "unsupported" (no OPFS or no Web Locks in this browser).
private fun claimDatabase(): Promise<JsString> = js("""
    new Promise((resolve) => {
        if (!navigator.locks || !navigator.storage || !navigator.storage.getDirectory) { resolve("unsupported"); return; }
        navigator.locks.request("reading-list-database", { ifAvailable: true }, (lock) => {
            resolve(lock ? "owner" : "busy");
            return lock ? new Promise(() => {}) : undefined;
        });
    })""")

/**
 * In the browser, SQLite runs in a web worker and keeps the file in the Origin Private File System.
 * Returns the database and, when it could only be opened in memory, why.
 */
suspend fun openDatabase(): Pair<BookDatabase, StorageWarning?> {
    val driver = WebWorkerSQLiteDriver(sqliteWorker())
    return when (claimDatabase().await<JsString>().toString()) {
        "owner" -> Room.databaseBuilder<BookDatabase>("reading-list.db").setDriver(driver).addMigrations(MIGRATION_2_3).build() to null
        "busy" -> Room.inMemoryDatabaseBuilder<BookDatabase>().setDriver(driver).build() to StorageWarning.OtherTab
        else -> Room.inMemoryDatabaseBuilder<BookDatabase>().setDriver(driver).build() to StorageWarning.NoStorage
    }
}
