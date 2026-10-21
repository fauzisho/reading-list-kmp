// a List as a Swift array
import ReadingKit

let titles: [String] = Library().books().map { $0.title }
