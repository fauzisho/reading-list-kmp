package dev.fauzisho.readinglist

import androidx.room3.withWriteTransaction
import dev.fauzisho.readinglist.sync.SyncRequest
import dev.fauzisho.readinglist.sync.SyncResponse
import dev.fauzisho.readinglist.sync.merge
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

/** The server, as the app sees it. An interface, so that tests can use the server's store directly. */
fun interface SyncApi {
    suspend fun sync(request: SyncRequest): SyncResponse
}

/** The real server, over HTTP. Ktor picks each platform's engine: OkHttp, Java's HttpClient, the browser's fetch, Darwin. */
class HttpSyncApi(private val baseUrl: String) : SyncApi {
    private val client = HttpClient {
        install(ContentNegotiation) { json() }
        install(HttpTimeout) { requestTimeoutMillis = 10_000 }
    }

    override suspend fun sync(request: SyncRequest): SyncResponse =
        client.post("$baseUrl/sync") { contentType(ContentType.Application.Json); setBody(request) }.body()
}

/**
 * One round of sync: send what changed here, merge what changed elsewhere. The local database stays the only truth
 * for the UI: sync writes into it, and the list on screen follows. Safe to repeat after any failure: sending the same
 * change twice merges to the same result, and nothing is marked sent until the server has answered.
 */
class Syncer(private val db: BookDatabase, private val repo: BookRepository, private val api: SyncApi) {
    private val dao = db.books()

    suspend fun sync(): Int {
        val since = dao.setting(CURSOR)?.toLongOrNull() ?: 0
        val sent = dao.dirty()
        val response = api.sync(SyncRequest(since, sent.map { it.toSync() }))   // throws when offline: nothing changes
        db.withWriteTransaction {
            for (remote in response.changes) {
                repo.observe(maxOf(remote.createdAt, remote.readAt, remote.notesAt, remote.deletedAt))
                val local = dao.byUid(remote.uid)
                if (local == null) dao.insert(remote.toBook(dirty = false))
                else {
                    val merged = merge(local.toSync(), remote)
                    dao.update(local.with(merged, dirty = local.dirty && merged != remote))
                }
            }
            for (b in sent) dao.markSent(b.uid, b.readAt, b.notesAt, b.deletedAt)
            dao.putSetting(Setting(CURSOR, response.cursor.toString()))
        }
        return response.changes.size
    }

    companion object { const val CURSOR = "sync-cursor" }
}
