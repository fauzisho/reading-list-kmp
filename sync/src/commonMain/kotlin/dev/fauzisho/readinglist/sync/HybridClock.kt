package dev.fauzisho.readinglist.sync

/**
 * Timestamps for edits: the device's wall clock, but never earlier than (or equal to) anything this device has
 * already seen. A device whose clock is an hour behind still stamps an edit later than the change it is replying
 * to, so "later wins" keeps meaning "later". (A simplified hybrid logical clock: a time in milliseconds and a
 * counter, folded into one Long.)
 */
class HybridClock(private val wallClock: () -> Long, private var last: Long = 0) {
    /** A timestamp for a new edit. */
    fun now(): Long {
        last = maxOf(wallClock(), last + 1)
        return last
    }

    /** Called with every timestamp received from elsewhere. */
    fun observe(timestamp: Long) {
        last = maxOf(last, timestamp)
    }

    val lastIssued: Long get() = last
}
