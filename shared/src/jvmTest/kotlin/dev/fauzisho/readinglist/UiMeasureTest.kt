package dev.fauzisho.readinglist

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import kotlinx.serialization.json.Json
import java.io.File
import javax.imageio.ImageIO
import kotlin.test.Test

/**
 * Not a check: renders the real App() offscreen on the desktop JVM (Skia, with this machine's system fonts) and records
 * at which window widths the list and a book appear side by side, plus a picture of titles in several scripts.
 * Output: build/ui-measure/desktop.json and glyphs-desktop.png.
 */
@OptIn(ExperimentalTestApi::class, ExperimentalComposeUiApi::class)
class UiMeasureTest {
    private val out = File("build/ui-measure").apply { mkdirs() }

    private fun services(books: List<Book>): PlatformServices {
        val store = MemoryStore().apply { values[BookRepository.LEGACY_KEY] = Json.encodeToString(books) }
        return PlatformServices(store, sharer = null, database = inMemoryDatabase(),
            history = FakeHistory(initial = BookDetail(2)))   // open on a book, as a link would
    }

    @Test fun recordWhereTwoPanesBegin() {
        val panes = (320..1400 step 20).associateWith { w ->
            var two = false
            runDesktopComposeUiTest(width = w, height = 900) {
                setContent { App(services(BookRepository.SAMPLE)) }
                waitUntil(timeoutMillis = 10_000) { onAllNodes(hasText("Notes")).fetchSemanticsNodes().isNotEmpty() }
                two = onAllNodes(hasText("Add")).fetchSemanticsNodes().isNotEmpty()   // the list's Add button is showing too
            }
            if (two) 2 else 1
        }
        File(out, "desktop.json").writeText(panes.entries.joinToString(",", "{", "}") { "\"${it.key}\":${it.value}" })
        println("PANES desktop " + panes.entries.joinToString(" ") { "${it.key}:${it.value}" })
    }

    @Test fun drawTitlesInSeveralScripts() = runDesktopComposeUiTest(width = 1000, height = 560) {
        setContent { App(services(GLYPH_BOOKS)) }
        waitUntil(timeoutMillis = 10_000) { onAllNodes(hasText("Notes")).fetchSemanticsNodes().isNotEmpty() }
        ImageIO.write(onRoot().captureToImage().toAwtImage(), "png", File(out, "glyphs-desktop.png"))
    }

    companion object {
        val GLYPH_BOOKS = listOf(
            Book(1, "Kotlin in Action", "Aigner"), Book(2, "ノルウェイの森", "村上春樹"), Book(3, "Война и мир", "Лев Толстой"),
            Book(4, "ألف ليلة وليلة", "مجهول"), Book(5, "Reading 📚 🙂", "emoji"),
        )
    }
}
