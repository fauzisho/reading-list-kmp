// a suspend function with async/await
import ReadingKit

func addOne() async throws -> Bool {
    return try await Library().add(title: "Dune", author: "Herbert")
}
