// an enum, switched exhaustively
import ReadingKit

func label(_ s: SyncState) -> String {
    switch s {
    case .unavailable: return "no server"
    case .never: return "not synced"
    case .syncing: return "syncing"
    case .done: return "up to date"
    case .offline: return "offline"
    }
}
