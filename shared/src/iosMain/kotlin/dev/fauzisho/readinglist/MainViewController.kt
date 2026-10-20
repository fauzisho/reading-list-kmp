package dev.fauzisho.readinglist

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIViewController

/** The simulator shares the Mac's network, so the demo server on the Mac is localhost. */
private const val SIMULATOR_SERVER = "http://localhost:8080"

/** Called from the Xcode project's SwiftUI or UIKit code. */
fun MainViewController(): UIViewController {
    lateinit var controller: UIViewController
    val sharer = Sharer { text -> controller.presentViewController(UIActivityViewController(listOf(text), null), animated = true, completion = null) }
    controller = ComposeUIViewController { App(PlatformServices(UserDefaultsStore(), sharer, openDatabase(), syncUrl = SIMULATOR_SERVER)) }
    return controller
}
