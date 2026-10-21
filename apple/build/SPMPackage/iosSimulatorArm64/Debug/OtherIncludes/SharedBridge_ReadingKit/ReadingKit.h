#include <Foundation/Foundation.h>
#include <stdint.h>

NS_ASSUME_NONNULL_BEGIN

NSString * dev_fauzisho_readinglist_apple_Book_author_get(void * self);

void * dev_fauzisho_readinglist_apple_Book_copy__TypesOfArguments__Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(void * self, int32_t id_, NSString * title, NSString * author, _Bool read, NSString * notes);

_Bool dev_fauzisho_readinglist_apple_Book_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable other);

int32_t dev_fauzisho_readinglist_apple_Book_hashCode(void * self);

int32_t dev_fauzisho_readinglist_apple_Book_id_get(void * self);

void * dev_fauzisho_readinglist_apple_Book_init_allocate();

_Bool dev_fauzisho_readinglist_apple_Book_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32_Swift_String_Swift_String_Swift_Bool_Swift_String__(void * __kt, int32_t id_, NSString * title, NSString * author, _Bool read, NSString * notes);

NSString * dev_fauzisho_readinglist_apple_Book_notes_get(void * self);

_Bool dev_fauzisho_readinglist_apple_Book_read_get(void * self);

NSString * dev_fauzisho_readinglist_apple_Book_title_get(void * self);

NSString * dev_fauzisho_readinglist_apple_Book_toString(void * self);

_Bool dev_fauzisho_readinglist_apple_Library_add__TypesOfArguments__Swift_String_Swift_String__(void * self, NSString * title, NSString * author, _Bool (^continuation)(_Bool), _Bool (^exception)(void * _Nullable ), void * cancellation);

NSArray<id> * dev_fauzisho_readinglist_apple_Library_books(void * self);

void * dev_fauzisho_readinglist_apple_Library_changes_get(void * self);

int64_t dev_fauzisho_readinglist_apple_Library_count(void * self);

void * _Nullable dev_fauzisho_readinglist_apple_Library_find__TypesOfArguments__Swift_Int32__(void * self, int32_t id_);

void * dev_fauzisho_readinglist_apple_Library_init_allocate();

_Bool dev_fauzisho_readinglist_apple_Library_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer__(void * __kt);

void * dev_fauzisho_readinglist_apple_Library_load__TypesOfArguments__Swift_Int32__(void * self, int32_t id_);

void * dev_fauzisho_readinglist_apple_Load_Done_copy__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable value);

_Bool dev_fauzisho_readinglist_apple_Load_Done_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable other);

int32_t dev_fauzisho_readinglist_apple_Load_Done_hashCode(void * self);

void * dev_fauzisho_readinglist_apple_Load_Done_init_allocate();

_Bool dev_fauzisho_readinglist_apple_Load_Done_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * __kt, void * _Nullable value);

NSString * dev_fauzisho_readinglist_apple_Load_Done_toString(void * self);

void * _Nullable dev_fauzisho_readinglist_apple_Load_Done_value_get(void * self);

_Bool dev_fauzisho_readinglist_apple_Load_Loading_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable other);

void * dev_fauzisho_readinglist_apple_Load_Loading_get();

int32_t dev_fauzisho_readinglist_apple_Load_Loading_hashCode(void * self);

NSString * dev_fauzisho_readinglist_apple_Load_Loading_toString(void * self);

void * dev_fauzisho_readinglist_apple_Route_BookDetail_copy__TypesOfArguments__Swift_Int32__(void * self, int32_t id_);

_Bool dev_fauzisho_readinglist_apple_Route_BookDetail_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable other);

int32_t dev_fauzisho_readinglist_apple_Route_BookDetail_hashCode(void * self);

int32_t dev_fauzisho_readinglist_apple_Route_BookDetail_id_get(void * self);

void * dev_fauzisho_readinglist_apple_Route_BookDetail_init_allocate();

_Bool dev_fauzisho_readinglist_apple_Route_BookDetail_init_initialize__TypesOfArguments__Swift_UnsafeMutableRawPointer_Swift_Int32__(void * __kt, int32_t id_);

NSString * dev_fauzisho_readinglist_apple_Route_BookDetail_toString(void * self);

_Bool dev_fauzisho_readinglist_apple_Route_BookList_equals__TypesOfArguments__Swift_Optional_anyU20KotlinRuntimeSupport__KotlinBridgeable___(void * self, void * _Nullable other);

void * dev_fauzisho_readinglist_apple_Route_BookList_get();

int32_t dev_fauzisho_readinglist_apple_Route_BookList_hashCode(void * self);

NSString * dev_fauzisho_readinglist_apple_Route_BookList_toString(void * self);

void * dev_fauzisho_readinglist_apple_SyncState_Done();

void * dev_fauzisho_readinglist_apple_SyncState_Never();

void * dev_fauzisho_readinglist_apple_SyncState_Offline();

void * dev_fauzisho_readinglist_apple_SyncState_Syncing();

void * dev_fauzisho_readinglist_apple_SyncState_Unavailable();

int32_t dev_fauzisho_readinglist_apple_SyncState_ordinal(void * self);

void * dev_fauzisho_readinglist_apple_merge__TypesOfArguments__ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__(void * a, void * b);

int32_t dev_fauzisho_readinglist_apple_readCount__TypesOfArguments__Swift_Array_ExportedKotlinPackages_dev_fauzisho_readinglist_apple_Book__Swift_Bool__(NSArray<id> * books, _Bool includeUnread);

NS_ASSUME_NONNULL_END
