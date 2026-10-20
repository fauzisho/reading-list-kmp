package dev.fauzisho.readinglist

import android.app.Activity
import android.content.Context
import android.content.Intent

class PrefsStore(context: Context) : KeyValueStore {
    private val prefs = context.getSharedPreferences("reading-list", Context.MODE_PRIVATE)
    override fun get(key: String): String? = prefs.getString(key, null)
    override fun put(key: String, value: String) = prefs.edit().putString(key, value).apply()
}

class IntentSharer(private val activity: Activity) : Sharer {
    override fun share(text: String) {
        val send = Intent(Intent.ACTION_SEND).setType("text/plain").putExtra(Intent.EXTRA_TEXT, text)
        activity.startActivity(Intent.createChooser(send, null))
    }
}

/** The Context comes in here, from the Activity, instead of through a global. */
fun androidServices(activity: Activity) =
    PlatformServices(PrefsStore(activity.applicationContext), IntentSharer(activity), openDatabase(activity),
        syncUrl = "http://10.0.2.2:8080")   // the emulator's name for the computer it runs on, where the demo server listens
