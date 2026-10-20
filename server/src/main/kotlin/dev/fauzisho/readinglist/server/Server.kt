package dev.fauzisho.readinglist.server

import dev.fauzisho.readinglist.sync.ServerStore
import dev.fauzisho.readinglist.sync.SyncRequest
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

/** The whole server: one endpoint to sync, one to look. The books live in memory, for as long as the server runs. */
fun Application.readingListServer(store: ServerStore = ServerStore()) {
    install(ContentNegotiation) { json() }
    install(CORS) {          // the browser build calls from another origin; fine for a local demo, too open for production
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Post)
    }
    routing {
        post("/sync") {
            val request = call.receive<SyncRequest>()
            call.respond(synchronized(store) { store.sync(request) })
        }
        get("/books") { call.respond(synchronized(store) { store.all() }) }
    }
}

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0") { readingListServer() }.start(wait = true)
}
