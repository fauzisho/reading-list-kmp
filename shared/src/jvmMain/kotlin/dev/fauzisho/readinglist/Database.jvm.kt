package dev.fauzisho.readinglist

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

fun openDatabase(file: File): BookDatabase {
    file.parentFile?.mkdirs()
    return Room.databaseBuilder<BookDatabase>(file.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addMigrations(MIGRATION_2_3)
        .build()
}
