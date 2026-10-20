@file:OptIn(ExperimentalWasmJsInterop::class)

package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.sqlite.driver.web.WebWorkerSQLiteDriver
import org.w3c.dom.Worker

private fun testWorker(): Worker = js("""new Worker(new URL("reading-list-sqlite-worker/worker.js", import.meta.url))""")

actual fun inMemoryDatabase(): BookDatabase = Room.inMemoryDatabaseBuilder<BookDatabase>().setDriver(WebWorkerSQLiteDriver(testWorker())).build()
