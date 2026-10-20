package dev.fauzisho.readinglist.sync

/**
 * Two versions of the same book, merged: for each field, the version with the later timestamp wins (ties go to
 * the larger value, so that every replica picks the same one). A deletion wins over everything: once deleted on
 * any device, a book stays deleted. The result does not depend on the order of the arguments, and merging a
 * version twice changes nothing, so devices and the server can merge in any order, any number of times.
 */
fun merge(a: SyncBook, b: SyncBook): SyncBook {
    require(a.uid == b.uid) { "different books: ${a.uid}, ${b.uid}" }
    fun <T : Comparable<T>> pick(va: T, ta: Long, vb: T, tb: Long): Pair<T, Long> = when {
        ta != tb -> if (ta > tb) va to ta else vb to tb
        else -> maxOf(va, vb) to ta
    }
    val (read, readAt) = pick(a.read, a.readAt, b.read, b.readAt)
    val (notes, notesAt) = pick(a.notes, a.notesAt, b.notes, b.notesAt)
    val first = if (a.createdAt <= b.createdAt) a else b   // title and author never change after the book is added
    return SyncBook(
        uid = a.uid, title = first.title, author = first.author, createdAt = first.createdAt,
        read = read, readAt = readAt, notes = notes, notesAt = notesAt,
        deleted = a.deleted || b.deleted,
        deletedAt = maxOf(if (a.deleted) a.deletedAt else 0, if (b.deleted) b.deletedAt else 0),
    )
}
