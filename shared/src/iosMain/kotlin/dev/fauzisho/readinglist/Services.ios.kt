package dev.fauzisho.readinglist

import platform.Foundation.NSUserDefaults

class UserDefaultsStore : KeyValueStore {
    private val defaults = NSUserDefaults.standardUserDefaults
    override fun get(key: String): String? = defaults.stringForKey(key)
    override fun put(key: String, value: String) = defaults.setObject(value, forKey = key)
}
