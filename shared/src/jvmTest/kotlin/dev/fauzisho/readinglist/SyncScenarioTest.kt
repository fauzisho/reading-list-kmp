package dev.fauzisho.readinglist

import dev.fauzisho.readinglist.server.readingListServer
import dev.fauzisho.readinglist.sync.ServerStore
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.IOException
import java.net.ServerSocket
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Two devices and the real server, over HTTP: each device is the app's own code (Room, the repository, Syncer, Ktor's
 * client) with an in-memory database, its own wall clock and a switch for its network. Every scenario is a story told
 * twice: here, with the app; and in [NaiveSync], with the simpler designs this one replaces. Both outcomes are written
 * to build/sync/scenarios.json.
 */
class SyncScenarioTest {
    private var realTime = 1_760_000_000_000L          // the true time, advanced by the stories
    private val port = ServerSocket(0).use { it.localPort }
    private val server = embeddedServer(Netty, port = port, host = "127.0.0.1") { readingListServer(ServerStore()) }.start(wait = false)

    inner class Device(skewMinutes: Long = 0) {
        val db = inMemoryDatabase()
        val repo = BookRepository(db, MemoryStore()) { realTime + skewMinutes * 60_000 }
        var online = true
        private val http = HttpSyncApi("http://127.0.0.1:$port")
        var loseNextResponse = false
        val syncer = Syncer(db, repo, SyncApi { request ->
            if (!online) throw IOException("offline")
            val response = http.sync(request)
            if (loseNextResponse) { loseNextResponse = false; throw IOException("connection dropped after the server answered") }
            response
        })

        suspend fun start() = repo.initialize()
        suspend fun sync() = runCatching { syncer.sync() }
        suspend fun books() = db.books().observeAll().first()
        suspend fun book(title: String) = books().first { it.title == title }
        suspend fun pending() = db.books().dirty().size
    }

    private fun later(minutes: Long = 1) { realTime += minutes * 60_000 }

    @AfterTest fun stop() = server.stop(100, 500)

    private val results = mutableMapOf<String, Map<String, Any?>>()

    private fun record(id: String, app: Any?, naive: Any?, ok: Boolean) {
        results[id] = mapOf("app" to app, "naive" to naive, "ok" to ok)
        val f = File("build/sync/scenarios-$id.json"); f.parentFile.mkdirs()
        f.writeText("{\"app\":${json(app)},\"naive\":${json(naive)},\"ok\":$ok}")
    }

    private fun json(v: Any?): String = when (v) {
        null -> "null"
        is String -> "\"" + v.replace("\"", "\\\"") + "\""
        is Boolean, is Number -> v.toString()
        is List<*> -> v.joinToString(",", "[", "]") { json(it) }
        is Map<*, *> -> v.entries.joinToString(",", "{", "}") { "\"${it.key}\":" + json(it.value) }
        else -> json(v.toString())
    }

    @Test fun bothAddABookWhileOffline() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start()
        a.online = false; b.online = false
        later(); a.repo.add("Dune", "Herbert"); later(); b.repo.add("Neuromancer", "Gibson")
        a.online = true; b.online = true
        a.sync(); b.sync(); a.sync()
        val onA = a.books().map { it.title }; val onB = b.books().map { it.title }
        val ok = onA == onB && "Dune" in onA && "Neuromancer" in onA && onA.size == 5
        record("add-offline", mapOf("A" to onA, "B" to onB), NaiveSync.addOffline(), ok)
        assertTrue(ok, "$onA / $onB")
    }

    @Test fun deletedOnOneDeviceWhileTheOtherStillHasIt() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start(); a.sync(); b.sync()
        b.online = false
        later(); a.repo.remove(a.book("Kotlin in Action").id); a.sync()
        later(); b.online = true; b.sync(); a.sync()
        val onA = a.books().map { it.title }; val onB = b.books().map { it.title }
        val ok = "Kotlin in Action" !in onA && "Kotlin in Action" !in onB
        record("delete", mapOf("A" to onA, "B" to onB), NaiveSync.delete(), ok)
        assertTrue(ok, "$onA / $onB")
    }

    @Test fun twoDevicesChangeDifferentFieldsOfOneBook() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start(); a.sync(); b.sync()
        a.online = false; b.online = false
        later(); a.repo.toggle(a.book("Kotlin in Action").id)
        later(); b.repo.setNotes(b.book("Kotlin in Action").id, "chapter 4: coroutines")
        a.online = true; b.online = true
        a.sync(); b.sync(); a.sync()
        val ka = a.book("Kotlin in Action"); val kb = b.book("Kotlin in Action")
        val app = mapOf("A" to mapOf("read" to ka.read, "notes" to ka.notes), "B" to mapOf("read" to kb.read, "notes" to kb.notes))
        val ok = ka.read && kb.read && ka.notes == "chapter 4: coroutines" && kb.notes == ka.notes
        record("different-fields", app, NaiveSync.differentFields(), ok)
        assertTrue(ok, app.toString())
    }

    @Test fun aLaterEditFromADeviceWhoseClockIsAnHourBehind() = runBlocking {
        val a = Device(); val b = Device(skewMinutes = -60); a.start(); b.start(); a.sync(); b.sync()
        later(); a.repo.setNotes(a.book("Kotlin in Action").id, "first thought"); a.sync()
        later(); b.sync()                                                  // B sees A's note…
        later(); b.repo.setNotes(b.book("Kotlin in Action").id, "second thought, written later"); b.sync()   // …and replies to it
        a.sync()
        val na = a.book("Kotlin in Action").notes; val nb = b.book("Kotlin in Action").notes
        val ok = na == "second thought, written later" && nb == na
        record("clock-skew", mapOf("A" to na, "B" to nb), NaiveSync.clockSkew(), ok)
        assertTrue(ok, "$na / $nb")
    }

    @Test fun theSameFieldChangedOnBothDevicesWhileOffline() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start(); a.sync(); b.sync()
        a.online = false; b.online = false
        later(); a.repo.setNotes(a.book("Kotlin in Action").id, "A's note")
        later(); b.repo.setNotes(b.book("Kotlin in Action").id, "B's note")
        a.online = true; b.online = true
        a.sync(); b.sync(); a.sync()
        val na = a.book("Kotlin in Action").notes; val nb = b.book("Kotlin in Action").notes
        val ok = na == nb && na == "B's note"                              // last writer wins: one of the two is lost, by design
        record("same-field", mapOf("A" to na, "B" to nb), NaiveSync.sameField(), ok)
        assertTrue(ok, "$na / $nb")
    }

    @Test fun bothAddTheSameTitleWhileOffline() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start()
        a.online = false; b.online = false
        later(); a.repo.add("Dune", "Herbert"); later(); b.repo.add("Dune", "Frank Herbert")
        a.online = true; b.online = true
        a.sync(); b.sync(); a.sync()
        val onA = a.books().filter { it.title == "Dune" }.map { it.author }; val onB = b.books().filter { it.title == "Dune" }.map { it.author }
        val ok = onA.size == 2 && onA == onB                               // both kept: no data lost, a duplicate to tidy up
        record("same-title", mapOf("A" to onA, "B" to onB), NaiveSync.sameTitle(), ok)
        assertTrue(ok, "$onA / $onB")
    }

    @Test fun theConnectionDropsAfterTheServerHasAnswered() = runBlocking {
        val a = Device(); val b = Device(); a.start(); b.start(); a.sync(); b.sync()
        later(); a.repo.toggle(a.book("Kotlin in Action").id)            // ticked as read
        a.loseNextResponse = true
        val first = a.sync().isSuccess                                     // the server applied it; A never heard back
        val pendingAfterFailure = a.pending()
        val second = a.sync().isSuccess                                    // so A sends it again
        b.sync()
        val readOnB = b.book("Kotlin in Action").read
        val app = mapOf("first sync" to first, "still pending" to pendingAfterFailure, "retry" to second, "pending after" to a.pending(), "read on B" to readOnB)
        val ok = !first && pendingAfterFailure == 1 && second && a.pending() == 0 && readOnB
        record("lost-response", app, NaiveSync.lostResponse(), ok)
        assertTrue(ok, app.toString())
    }
}
