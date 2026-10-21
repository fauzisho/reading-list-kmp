// copy() of a data class
import ReadingKit

let b = Book(id: 1, title: "Dune", author: "Herbert", read: false, notes: "")
let read = b.copy(read: true)
