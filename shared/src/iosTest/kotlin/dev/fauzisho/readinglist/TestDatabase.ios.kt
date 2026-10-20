package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

actual fun inMemoryDatabase(): BookDatabase = Room.inMemoryDatabaseBuilder<BookDatabase>().setDriver(BundledSQLiteDriver()).build()
