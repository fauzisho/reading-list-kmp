// a sealed interface, switched exhaustively
import ReadingKit

func screen(_ r: Route) -> String {
    switch r {
    case .bookList: return "list"
    case .bookDetail(let d): return "book \(d.id)"
    }
}
