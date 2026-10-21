// a sealed interface, switched exhaustively through sealedType(), the way Swift export intends it
import ReadingKit

func screen(_ r: Route) -> String {
    switch r.sealedType() {
    case .bookList: return "list"
    case .bookDetail(let d): return "book \(d.value.id)"
    }
}
