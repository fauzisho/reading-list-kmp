@file:kotlin.Suppress("DEPRECATION_ERROR")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Book::class, "22ExportedKotlinPackages3devO8fauzishoO11readinglistO5appleO10ReadingKitE4BookC")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Library::class, "22ExportedKotlinPackages3devO8fauzishoO11readinglistO5appleO10ReadingKitE7LibraryC")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Load::class, "_Load")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Route::class, "_Route")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Load.Done::class, "10ReadingKit64_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_DoneC")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Load.Loading::class, "10ReadingKit67_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Load_LoadingC")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Route.BookDetail::class, "10ReadingKit71_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookDetailC")
@file:kotlin.native.internal.objc.BindClassToObjCName(dev.fauzisho.readinglist.apple.Route.BookList::class, "10ReadingKit69_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Route_BookListC")

import kotlin.native.internal.ExportedBridge
import kotlinx.cinterop.*
import kotlinx.cinterop.internal.convertBlockPtrToKotlinFunction
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch as kotlinx_coroutines_launch

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_author_get")
public fun dev_fauzisho_readinglist_apple_Book_author_get(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.author }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_copy__TypesOfArguments__Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__")
public fun dev_fauzisho_readinglist_apple_Book_copy__TypesOfArguments__Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(self: kotlin.native.internal.NativePtr, id: Int, title: kotlin.native.internal.NativePtr, author: kotlin.native.internal.NativePtr, read: Boolean, notes: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val __id = id
    val __title = interpretObjCPointer<kotlin.String>(title)
    val __author = interpretObjCPointer<kotlin.String>(author)
    val __read = read
    val __notes = interpretObjCPointer<kotlin.String>(notes)
    val _result = run { __self.copy(__id, __title, __author, __read, __notes) }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Book_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, other: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val __other = if (other == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(other) as kotlin.Any
    val _result = run { __self.equals(__other) }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_hashCode")
public fun dev_fauzisho_readinglist_apple_Book_hashCode(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.hashCode() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_id_get")
public fun dev_fauzisho_readinglist_apple_Book_id_get(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.id }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_init_allocate")
public fun dev_fauzisho_readinglist_apple_Book_init_allocate(): kotlin.native.internal.NativePtr {
    val _result = run { kotlin.native.internal.createUninitializedInstance<dev.fauzisho.readinglist.apple.Book>() }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__")
public fun dev_fauzisho_readinglist_apple_Book_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(__kt: kotlin.native.internal.NativePtr, id: Int, title: kotlin.native.internal.NativePtr, author: kotlin.native.internal.NativePtr, read: Boolean, notes: kotlin.native.internal.NativePtr): Boolean {
    val ____kt = kotlin.native.internal.ref.dereferenceExternalRCRef(__kt)!!
    val __id = id
    val __title = interpretObjCPointer<kotlin.String>(title)
    val __author = interpretObjCPointer<kotlin.String>(author)
    val __read = read
    val __notes = interpretObjCPointer<kotlin.String>(notes)
    val _result = run { kotlin.native.internal.initInstance(____kt, dev.fauzisho.readinglist.apple.Book(__id, __title, __author, __read, __notes)) }
    return run { _result; true }
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_notes_get")
public fun dev_fauzisho_readinglist_apple_Book_notes_get(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.notes }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_read_get")
public fun dev_fauzisho_readinglist_apple_Book_read_get(self: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.read }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_title_get")
public fun dev_fauzisho_readinglist_apple_Book_title_get(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.title }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Book_toString")
public fun dev_fauzisho_readinglist_apple_Book_toString(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Book
    val _result = run { __self.toString() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_add__TypesOfArguments__Swift_String_Swift_String__")
public fun dev_fauzisho_readinglist_apple_Library_add__TypesOfArguments__Swift_String_Swift_String__(self: kotlin.native.internal.NativePtr, title: kotlin.native.internal.NativePtr, author: kotlin.native.internal.NativePtr, continuation: kotlin.native.internal.NativePtr, exception: kotlin.native.internal.NativePtr, cancellation: kotlin.native.internal.NativePtr): Unit {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val __title = interpretObjCPointer<kotlin.String>(title)
    val __author = interpretObjCPointer<kotlin.String>(author)
    val __continuation = run {
        val kotlinFun = convertBlockPtrToKotlinFunction<(Boolean)->Boolean>(continuation);
        { arg0: Boolean ->
            val _arg0 = arg0
            val _result = kotlinFun(_arg0)
            run<Unit> { _result }
        }
    }
    val __exception = run {
        val kotlinFun = convertBlockPtrToKotlinFunction<(kotlin.native.internal.NativePtr)->Boolean>(exception);
        { arg0: kotlin.Any? ->
            val _arg0 = if (arg0 == null) kotlin.native.internal.NativePtr.NULL else kotlin.native.internal.ref.createRetainedExternalRCRef(arg0)
            val _result = kotlinFun(_arg0)
            run<Unit> { _result }
        }
    }
    val __cancellation = kotlin.native.internal.ref.dereferenceExternalRCRef(cancellation) as SwiftJob
    swiftCoroutine(__continuation, __exception, __cancellation) {
        __self.add(__title, __author)
    }
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_books")
public fun dev_fauzisho_readinglist_apple_Library_books(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val _result = run { __self.books() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_changes_get")
public fun dev_fauzisho_readinglist_apple_Library_changes_get(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val _result = run { __self.changes }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_count")
public fun dev_fauzisho_readinglist_apple_Library_count(self: kotlin.native.internal.NativePtr): Long {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val _result = run { __self.count() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_find__TypesOfArguments__Swift_Int32__")
public fun dev_fauzisho_readinglist_apple_Library_find__TypesOfArguments__Swift_Int32__(self: kotlin.native.internal.NativePtr, id: Int): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val __id = id
    val _result = run { __self.find(__id) }
    return if (_result == null) kotlin.native.internal.NativePtr.NULL else kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_init_allocate")
public fun dev_fauzisho_readinglist_apple_Library_init_allocate(): kotlin.native.internal.NativePtr {
    val _result = run { kotlin.native.internal.createUninitializedInstance<dev.fauzisho.readinglist.apple.Library>() }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__")
public fun dev_fauzisho_readinglist_apple_Library_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(__kt: kotlin.native.internal.NativePtr): Boolean {
    val ____kt = kotlin.native.internal.ref.dereferenceExternalRCRef(__kt)!!
    val _result = run { kotlin.native.internal.initInstance(____kt, dev.fauzisho.readinglist.apple.Library()) }
    return run { _result; true }
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Library_load__TypesOfArguments__Swift_Int32__")
public fun dev_fauzisho_readinglist_apple_Library_load__TypesOfArguments__Swift_Int32__(self: kotlin.native.internal.NativePtr, id: Int): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Library
    val __id = id
    val _result = run { __self.load(__id) }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_copy__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Load_Done_copy__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, value: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>
    val __value = if (value == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(value) as kotlin.Any
    val _result = run { __self.copy(__value) }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Load_Done_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, other: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>
    val __other = if (other == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(other) as kotlin.Any
    val _result = run { __self.equals(__other) }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_hashCode")
public fun dev_fauzisho_readinglist_apple_Load_Done_hashCode(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>
    val _result = run { __self.hashCode() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_init_allocate")
public fun dev_fauzisho_readinglist_apple_Load_Done_init_allocate(): kotlin.native.internal.NativePtr {
    val _result = run { kotlin.native.internal.createUninitializedInstance<dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>>() }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Load_Done_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(__kt: kotlin.native.internal.NativePtr, value: kotlin.native.internal.NativePtr): Boolean {
    val ____kt = kotlin.native.internal.ref.dereferenceExternalRCRef(__kt)!!
    val __value = if (value == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(value) as kotlin.Any
    val _result = run { kotlin.native.internal.initInstance(____kt, dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>(__value)) }
    return run { _result; true }
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_toString")
public fun dev_fauzisho_readinglist_apple_Load_Done_toString(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>
    val _result = run { __self.toString() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Done_value_get")
public fun dev_fauzisho_readinglist_apple_Load_Done_value_get(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Done<kotlin.Any?>
    val _result = run { __self.value }
    return if (_result == null) kotlin.native.internal.NativePtr.NULL else kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Loading_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Load_Loading_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, other: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Loading
    val __other = if (other == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(other) as kotlin.Any
    val _result = run { __self.equals(__other) }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Loading_get")
public fun dev_fauzisho_readinglist_apple_Load_Loading_get(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.Load.Loading }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Loading_hashCode")
public fun dev_fauzisho_readinglist_apple_Load_Loading_hashCode(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Loading
    val _result = run { __self.hashCode() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Load_Loading_toString")
public fun dev_fauzisho_readinglist_apple_Load_Loading_toString(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Load.Loading
    val _result = run { __self.toString() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_copy__TypesOfArguments__Swift_Int32__")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_copy__TypesOfArguments__Swift_Int32__(self: kotlin.native.internal.NativePtr, id: Int): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookDetail
    val __id = id
    val _result = run { __self.copy(__id) }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, other: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookDetail
    val __other = if (other == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(other) as kotlin.Any
    val _result = run { __self.equals(__other) }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_hashCode")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_hashCode(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookDetail
    val _result = run { __self.hashCode() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_id_get")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_id_get(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookDetail
    val _result = run { __self.id }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_init_allocate")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_init_allocate(): kotlin.native.internal.NativePtr {
    val _result = run { kotlin.native.internal.createUninitializedInstance<dev.fauzisho.readinglist.apple.Route.BookDetail>() }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32__")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32__(__kt: kotlin.native.internal.NativePtr, id: Int): Boolean {
    val ____kt = kotlin.native.internal.ref.dereferenceExternalRCRef(__kt)!!
    val __id = id
    val _result = run { kotlin.native.internal.initInstance(____kt, dev.fauzisho.readinglist.apple.Route.BookDetail(__id)) }
    return run { _result; true }
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookDetail_toString")
public fun dev_fauzisho_readinglist_apple_Route_BookDetail_toString(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookDetail
    val _result = run { __self.toString() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookList_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___")
public fun dev_fauzisho_readinglist_apple_Route_BookList_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(self: kotlin.native.internal.NativePtr, other: kotlin.native.internal.NativePtr): Boolean {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookList
    val __other = if (other == kotlin.native.internal.NativePtr.NULL) null else kotlin.native.internal.ref.dereferenceExternalRCRef(other) as kotlin.Any
    val _result = run { __self.equals(__other) }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookList_get")
public fun dev_fauzisho_readinglist_apple_Route_BookList_get(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.Route.BookList }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookList_hashCode")
public fun dev_fauzisho_readinglist_apple_Route_BookList_hashCode(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookList
    val _result = run { __self.hashCode() }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_Route_BookList_toString")
public fun dev_fauzisho_readinglist_apple_Route_BookList_toString(self: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.Route.BookList
    val _result = run { __self.toString() }
    return _result.objcPtr()
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_Done")
public fun dev_fauzisho_readinglist_apple_SyncState_Done(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.SyncState.Done }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_Never")
public fun dev_fauzisho_readinglist_apple_SyncState_Never(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.SyncState.Never }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_Offline")
public fun dev_fauzisho_readinglist_apple_SyncState_Offline(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.SyncState.Offline }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_Syncing")
public fun dev_fauzisho_readinglist_apple_SyncState_Syncing(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.SyncState.Syncing }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_Unavailable")
public fun dev_fauzisho_readinglist_apple_SyncState_Unavailable(): kotlin.native.internal.NativePtr {
    val _result = run { dev.fauzisho.readinglist.apple.SyncState.Unavailable }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_SyncState_ordinal")
public fun dev_fauzisho_readinglist_apple_SyncState_ordinal(self: kotlin.native.internal.NativePtr): Int {
    val __self = kotlin.native.internal.ref.dereferenceExternalRCRef(self) as dev.fauzisho.readinglist.apple.SyncState
    val _result = run { __self.ordinal }
    return _result
}

@ExportedBridge("dev_fauzisho_readinglist_apple_merge__TypesOfArguments__ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__")
public fun dev_fauzisho_readinglist_apple_merge__TypesOfArguments__ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__(a: kotlin.native.internal.NativePtr, b: kotlin.native.internal.NativePtr): kotlin.native.internal.NativePtr {
    val __a = kotlin.native.internal.ref.dereferenceExternalRCRef(a) as dev.fauzisho.readinglist.apple.Book
    val __b = kotlin.native.internal.ref.dereferenceExternalRCRef(b) as dev.fauzisho.readinglist.apple.Book
    val _result = run { dev.fauzisho.readinglist.apple.merge(__a, __b) }
    return kotlin.native.internal.ref.createRetainedExternalRCRef(_result)
}

@ExportedBridge("dev_fauzisho_readinglist_apple_readCount__TypesOfArguments__Swift_Array_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__Swift_Bool__")
public fun dev_fauzisho_readinglist_apple_readCount__TypesOfArguments__Swift_Array_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__Swift_Bool__(books: kotlin.native.internal.NativePtr, includeUnread: Boolean): Int {
    val __books = interpretObjCPointer<kotlin.collections.List<dev.fauzisho.readinglist.apple.Book>>(books)
    val __includeUnread = includeUnread
    val _result = run { dev.fauzisho.readinglist.apple.readCount(__books, __includeUnread) }
    return _result
}

