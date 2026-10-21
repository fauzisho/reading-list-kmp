// a generic sealed type, its value read
import ReadingKit

func title(_ l: Library) -> String {
    if let done = l.load(id: 1) as? Load.Done<Book> { return done.value.title }
    return "loading"
}
