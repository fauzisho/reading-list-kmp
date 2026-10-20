package dev.fauzisho.readinglist

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import kotlinx.coroutines.Dispatchers

private var database: BookDatabase? = null

/**
 * One database per process, not per Activity: ViewModels outlive a recreated Activity (a rotation),
 * and the database they read from must too.
 */
@Synchronized
fun openDatabase(context: Context): BookDatabase = database ?: run {
    val app = context.applicationContext
    Room.databaseBuilder<BookDatabase>(app, app.getDatabasePath("reading-list.db").absolutePath)
        .setDriver(AndroidSQLiteDriver())   // Android's own SQLite; BundledSQLiteDriver would add 1.1–1.3 MB per CPU type
        .setQueryCoroutineContext(Dispatchers.IO)
        .addMigrations(MIGRATION_2_3)
        .build()
        .also { database = it }
}
