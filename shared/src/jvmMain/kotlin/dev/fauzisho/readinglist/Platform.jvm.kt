package dev.fauzisho.readinglist

// Proper nouns only: this text is not translated
actual fun platformName(): String = "JVM ${System.getProperty("java.version")} · ${System.getProperty("os.name")}"
