// a sealed interface, matched by type
import ReadingKit

func screen(_ r: Route) -> String {
    if let d = r as? Route.BookDetail { return "book \(d.id)" }
    return "list"
}
