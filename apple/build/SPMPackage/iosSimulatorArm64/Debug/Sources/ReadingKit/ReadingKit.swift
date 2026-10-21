@_exported import ExportedKotlinPackages
@_exported import KotlinCoroutineSupport
import KotlinRuntime
import KotlinRuntimeSupport
import OrgJetbrainsKotlinxKotlinxCoroutinesCore
@_implementationOnly import SharedBridge_ReadingKit

public typealias Book = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book
public typealias Library = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Library
public typealias Load = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load
public typealias Load_SealedType = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load_SealedType
public typealias Route = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route
public typealias Route_SealedType = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route_SealedType
public typealias SyncState = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.SyncState
public typealias _Load = ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Load
public typealias _Route = ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Route
public typealias __Load = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Load
public typealias __Route = ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Route
public final class _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done: KotlinRuntime.KotlinBase {
    public var value: (any KotlinRuntimeSupport._KotlinBridgeable)? {
        get {
            return { switch dev_fauzisho_readinglist_apple_Load_Done_value_get(self.__externalRCRef()) { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createBridgeable(externalRCRef: res); } }()
        }
    }
    public init(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) {
        let __kt = dev_fauzisho_readinglist_apple_Load_Done_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { dev_fauzisho_readinglist_apple_Load_Done_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(__kt, value.map { it in it.__externalRCRef() } ?? nil); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public static func ==(
        this: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done,
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        this.equals(other: other)
    }
    public func copy(
        value: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done {
        return ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Load_Done_copy__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), value.map { it in it.__externalRCRef() } ?? nil))
    }
    public func equals(
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return dev_fauzisho_readinglist_apple_Load_Done_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), other.map { it in it.__externalRCRef() } ?? nil)
    }
    public func hashCode() -> Swift.Int32 {
        return dev_fauzisho_readinglist_apple_Load_Done_hashCode(self.__externalRCRef())
    }
    public func toString() -> Swift.String {
        return dev_fauzisho_readinglist_apple_Load_Done_toString(self.__externalRCRef())
    }
}
public final class _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading: KotlinRuntime.KotlinBase {
    public static var shared: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading {
        get {
            return ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Load_Loading_get())
        }
    }
    private init() {
        fatalError()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public static func ==(
        this: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading,
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        this.equals(other: other)
    }
    public func equals(
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return dev_fauzisho_readinglist_apple_Load_Loading_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), other.map { it in it.__externalRCRef() } ?? nil)
    }
    public func hashCode() -> Swift.Int32 {
        return dev_fauzisho_readinglist_apple_Load_Loading_hashCode(self.__externalRCRef())
    }
    public func toString() -> Swift.String {
        return dev_fauzisho_readinglist_apple_Load_Loading_toString(self.__externalRCRef())
    }
}
public final class _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail: KotlinRuntime.KotlinBase, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Route {
    public var id: Swift.Int32 {
        get {
            return dev_fauzisho_readinglist_apple_Route_BookDetail_id_get(self.__externalRCRef())
        }
    }
    public init(
        id: Swift.Int32
    ) {
        let __kt = dev_fauzisho_readinglist_apple_Route_BookDetail_init_allocate()
        super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
        { dev_fauzisho_readinglist_apple_Route_BookDetail_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32__(__kt, id); return () }()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public static func ==(
        this: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail,
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        this.equals(other: other)
    }
    public func copy(
        id: Swift.Int32
    ) -> ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail {
        return ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Route_BookDetail_copy__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), id))
    }
    public func equals(
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return dev_fauzisho_readinglist_apple_Route_BookDetail_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), other.map { it in it.__externalRCRef() } ?? nil)
    }
    public func hashCode() -> Swift.Int32 {
        return dev_fauzisho_readinglist_apple_Route_BookDetail_hashCode(self.__externalRCRef())
    }
    public func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route_SealedType {
        .bookDetail(.init(self))
    }
    public func toString() -> Swift.String {
        return dev_fauzisho_readinglist_apple_Route_BookDetail_toString(self.__externalRCRef())
    }
}
public final class _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList: KotlinRuntime.KotlinBase, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Route {
    public static var shared: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList {
        get {
            return ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Route_BookList_get())
        }
    }
    private init() {
        fatalError()
    }
    package override init(
        __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
        options: KotlinRuntime.KotlinBaseConstructionOptions
    ) {
        super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
    }
    public static func ==(
        this: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList,
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        this.equals(other: other)
    }
    public func equals(
        other: (any KotlinRuntimeSupport._KotlinBridgeable)?
    ) -> Swift.Bool {
        return dev_fauzisho_readinglist_apple_Route_BookList_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), other.map { it in it.__externalRCRef() } ?? nil)
    }
    public func hashCode() -> Swift.Int32 {
        return dev_fauzisho_readinglist_apple_Route_BookList_hashCode(self.__externalRCRef())
    }
    public func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route_SealedType {
        .bookList(.init(self))
    }
    public func toString() -> Swift.String {
        return dev_fauzisho_readinglist_apple_Route_BookList_toString(self.__externalRCRef())
    }
}
public struct _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done
    init(
        _ value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading
    init(
        _ value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail
    init(
        _ value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail
    ) {
        self.value = value
    }
}
public struct _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList_SealedType: KotlinRuntimeSupport.SealedType {
    public let value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList
    init(
        _ value: ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList
    ) {
        self.value = value
    }
}
public func merge(
    a: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book,
    b: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book
) -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book {
    ExportedKotlinPackages.dev.fauzisho.readinglist.apple.merge(a: a, b: b)
}
public func readCount(
    books: [ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book],
    includeUnread: Swift.Bool
) -> Swift.Int32 {
    ExportedKotlinPackages.dev.fauzisho.readinglist.apple.readCount(books: books, includeUnread: includeUnread)
}
extension ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load where Self : ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Load {
    public func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load_SealedType {
        fatalError("must implement sealedType in subclass")
    }
}
extension ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load {
    typealias Done = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done
    typealias Loading = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading
    typealias _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done_SealedType = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done_SealedType
    typealias _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading_SealedType = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading_SealedType
}
extension ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route where Self : ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Route {
    public func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route_SealedType {
        fatalError("must implement sealedType in subclass")
    }
}
extension ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route {
    typealias BookDetail = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail
    typealias BookList = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList
    typealias _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail_SealedType = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail_SealedType
    typealias _ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList_SealedType = ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList_SealedType
}
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Route where Wrapped : ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Route {
}
extension KotlinRuntimeSupport._KotlinExistential: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load, ExportedKotlinPackages.dev.fauzisho.readinglist.apple.__Load where Wrapped : ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Load {
}
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Route {
}
extension KotlinRuntimeSupport._KotlinExistentialPenBox: ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Load {
}
extension ExportedKotlinPackages.dev.fauzisho.readinglist.apple {
    public enum Load_SealedType: KotlinRuntimeSupport.SealedType {
        case done(ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Done_SealedType)
        case loading(ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_Loading_SealedType)
        public var value: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load {
            get {
                switch self {
                case let .done(type): type.value
                case let .loading(type): type.value
                }
            }
        }
    }
    public enum Route_SealedType: KotlinRuntimeSupport.SealedType {
        case bookDetail(ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetail_SealedType)
        case bookList(ReadingKit._ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookList_SealedType)
        public var value: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route {
            get {
                switch self {
                case let .bookDetail(type): type.value
                case let .bookList(type): type.value
                }
            }
        }
    }
    public enum SyncState: KotlinRuntimeSupport._KotlinBridgeable, Swift.CaseIterable, Swift.LosslessStringConvertible, Swift.RawRepresentable {
        case Unavailable
        case Never
        case Syncing
        case Done
        case Offline
        public var description: Swift.String {
            get {
                switch self {
                case .Unavailable: "Unavailable"
                case .Never: "Never"
                case .Syncing: "Syncing"
                case .Done: "Done"
                case .Offline: "Offline"
                default: fatalError()
                }
            }
        }
        public var rawValue: Swift.Int32 {
            get {
                switch self {
                case .Unavailable: 0
                case .Never: 1
                case .Syncing: 2
                case .Done: 3
                case .Offline: 4
                default: fatalError()
                }
            }
        }
        public init?(
            _ description: Swift.String
        ) {
            switch description {
            case "Unavailable": self = .Unavailable
            case "Never": self = .Never
            case "Syncing": self = .Syncing
            case "Done": self = .Done
            case "Offline": self = .Offline
            default: return nil
            }
        }
        public init?(
            rawValue: Swift.Int32
        ) {
            guard 0..<5 ~= rawValue else { return nil }
            self = SyncState.allCases[Int(rawValue)]
        }
        public init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer!,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            switch dev_fauzisho_readinglist_apple_SyncState_ordinal(__externalRCRefUnsafe) {
            case 0: self = .Unavailable
            case 1: self = .Never
            case 2: self = .Syncing
            case 3: self = .Done
            case 4: self = .Offline
            default: fatalError()
            }
        }
        public func __externalRCRef() -> Swift.UnsafeMutableRawPointer! {
            return switch self {
            case .Unavailable: dev_fauzisho_readinglist_apple_SyncState_Unavailable()
            case .Never: dev_fauzisho_readinglist_apple_SyncState_Never()
            case .Syncing: dev_fauzisho_readinglist_apple_SyncState_Syncing()
            case .Done: dev_fauzisho_readinglist_apple_SyncState_Done()
            case .Offline: dev_fauzisho_readinglist_apple_SyncState_Offline()
            default: fatalError()
            }
        }
    }
    public protocol Load: KotlinRuntime.KotlinBase, ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Load {
        func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load_SealedType
    }
    public protocol Route: KotlinRuntime.KotlinBase, ExportedKotlinPackages.dev.fauzisho.readinglist.apple._Route {
        func sealedType() -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Route_SealedType
    }
    @objc(_Load)
    public protocol _Load {
    }
    @objc(_Route)
    public protocol _Route {
    }
    public protocol __Load: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public protocol __Route: KotlinRuntimeSupport._KotlinBridgeable {
    }
    public final class Book: KotlinRuntime.KotlinBase {
        public var author: Swift.String {
            get {
                return dev_fauzisho_readinglist_apple_Book_author_get(self.__externalRCRef())
            }
        }
        public var id: Swift.Int32 {
            get {
                return dev_fauzisho_readinglist_apple_Book_id_get(self.__externalRCRef())
            }
        }
        public var notes: Swift.String {
            get {
                return dev_fauzisho_readinglist_apple_Book_notes_get(self.__externalRCRef())
            }
        }
        public var read: Swift.Bool {
            get {
                return dev_fauzisho_readinglist_apple_Book_read_get(self.__externalRCRef())
            }
        }
        public var title: Swift.String {
            get {
                return dev_fauzisho_readinglist_apple_Book_title_get(self.__externalRCRef())
            }
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public init(
            id: Swift.Int32,
            title: Swift.String,
            author: Swift.String,
            read: Swift.Bool,
            notes: Swift.String
        ) {
            let __kt = dev_fauzisho_readinglist_apple_Book_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { dev_fauzisho_readinglist_apple_Book_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(__kt, id, title, author, read, notes); return () }()
        }
        public static func ==(
            this: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book,
            other: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Bool {
            this.equals(other: other)
        }
        public func copy(
            id: Swift.Int32,
            title: Swift.String,
            author: Swift.String,
            read: Swift.Bool,
            notes: Swift.String
        ) -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book {
            return ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Book_copy__TypesOfArguments__Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(self.__externalRCRef(), id, title, author, read, notes))
        }
        public func equals(
            other: (any KotlinRuntimeSupport._KotlinBridgeable)?
        ) -> Swift.Bool {
            return dev_fauzisho_readinglist_apple_Book_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self.__externalRCRef(), other.map { it in it.__externalRCRef() } ?? nil)
        }
        public func hashCode() -> Swift.Int32 {
            return dev_fauzisho_readinglist_apple_Book_hashCode(self.__externalRCRef())
        }
        public func toString() -> Swift.String {
            return dev_fauzisho_readinglist_apple_Book_toString(self.__externalRCRef())
        }
    }
    public final class Library: KotlinRuntime.KotlinBase {
        public var changes: any KotlinCoroutineSupport.KotlinTypedFlow<Swift.Array<ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book>> {
            get {
                return KotlinCoroutineSupport._KotlinTypedFlowImpl<Swift.Array<ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book>>(KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Library_changes_get(self.__externalRCRef())) as! any ExportedKotlinPackages.kotlinx.coroutines.flow.Flow)
            }
        }
        public init() {
            let __kt = dev_fauzisho_readinglist_apple_Library_init_allocate()
            super.init(__externalRCRefUnsafe: __kt, options: .asBoundBridge);
            { dev_fauzisho_readinglist_apple_Library_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt); return () }()
        }
        package override init(
            __externalRCRefUnsafe: Swift.UnsafeMutableRawPointer?,
            options: KotlinRuntime.KotlinBaseConstructionOptions
        ) {
            super.init(__externalRCRefUnsafe: __externalRCRefUnsafe, options: options);
        }
        public func add(
            title: Swift.String,
            author: Swift.String
        ) async throws -> Swift.Bool {
            try await withKotlinContinuation { continuation, exception, cancellation in
                let _: Bool = dev_fauzisho_readinglist_apple_Library_add__TypesOfArguments__Swift_String_Swift_String__(self.__externalRCRef(), title, author, {
                    let originalBlock: (Swift.Bool) -> Swift.Void = continuation
                    return { (arg0: Swift.Bool) in
                        let _arg0: Swift.Bool = arg0
                        let _result = originalBlock(_arg0)
                        return { _result; return true }()
                    }
                }(), {
                    let originalBlock: (Swift.Optional<KotlinRuntime.KotlinBase>) -> Swift.Void = exception
                    return { (arg0: Swift.UnsafeMutableRawPointer?) in
                        let _arg0: Swift.Optional<KotlinRuntime.KotlinBase> = { switch arg0 { case nil: .none; case let res?: KotlinRuntime.KotlinBase.__createClassWrapper(externalRCRef: res); } }()
                        let _result = originalBlock(_arg0)
                        return { _result; return true }()
                    }
                }(), cancellation.__externalRCRef())
            }
        }
        public func books() -> [ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book] {
            return dev_fauzisho_readinglist_apple_Library_books(self.__externalRCRef()) as! Swift.Array<ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book>
        }
        public func count() -> Swift.Int64 {
            return dev_fauzisho_readinglist_apple_Library_count(self.__externalRCRef())
        }
        public func find(
            id: Swift.Int32
        ) -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book? {
            return { switch dev_fauzisho_readinglist_apple_Library_find__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), id) { case nil: .none; case let res?: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book.__createClassWrapper(externalRCRef: res); } }()
        }
        public func load(
            id: Swift.Int32
        ) -> any ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load {
            return KotlinRuntime.KotlinBase.__createProtocolWrapper(externalRCRef: dev_fauzisho_readinglist_apple_Library_load__TypesOfArguments__Swift_Int32__(self.__externalRCRef(), id)) as! any ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Load
        }
    }
    public static func merge(
        a: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book,
        b: ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book
    ) -> ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book {
        return ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book.__createClassWrapper(externalRCRef: dev_fauzisho_readinglist_apple_merge__TypesOfArguments__ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__(a.__externalRCRef(), b.__externalRCRef()))
    }
    public static func readCount(
        books: [ExportedKotlinPackages.dev.fauzisho.readinglist.apple.Book],
        includeUnread: Swift.Bool
    ) -> Swift.Int32 {
        return dev_fauzisho_readinglist_apple_readCount__TypesOfArguments__Swift_Array_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__Swift_Bool__(books, includeUnread)
    }
}
