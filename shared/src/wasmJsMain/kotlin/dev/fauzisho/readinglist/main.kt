package dev.fauzisho.readinglist

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    MainScope().launch {
        val services = webServices()   // waits for the database lock: which tab owns the saved list
        ComposeViewport(document.body!!) { App(services) }
    }
}
