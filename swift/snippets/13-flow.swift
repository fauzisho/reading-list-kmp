// a Flow with for await
import ReadingKit

func watch(_ l: Library) async throws {
    for try await books in l.changes { print(books.count) }
}
