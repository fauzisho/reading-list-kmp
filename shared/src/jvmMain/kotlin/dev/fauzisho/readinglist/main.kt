package dev.fauzisho.readinglist

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    val services = desktopServices()
    application {
        Window(onCloseRequest = ::exitApplication, title = "Reading list") { App(services) }
    }
}
