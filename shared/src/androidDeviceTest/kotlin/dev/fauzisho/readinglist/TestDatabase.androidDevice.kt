package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import androidx.test.platform.app.InstrumentationRegistry

// On the device: the same driver as the app, Android's own SQLite.
actual fun inMemoryDatabase(): BookDatabase =
    Room.inMemoryDatabaseBuilder<BookDatabase>(InstrumentationRegistry.getInstrumentation().targetContext)
        .setDriver(AndroidSQLiteDriver())
        .build()
