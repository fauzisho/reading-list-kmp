// swift-tools-version: 5.9

import PackageDescription
let package = Package(
    name: "ReadingKit",
    products: [
        .library(
            name: "ReadingKitLibrary",
            targets: ["ExportedKotlinPackages", "KotlinRuntimeSupport", "KotlinCoroutineSupport", "ReadingKit", "OrgJetbrainsKotlinxKotlinxCoroutinesCore"]
        )
    ],
    targets: [
        .target(
            name: "ExportedKotlinPackages",
            dependencies: ["KotlinRuntime"]
        ),
        .target(
            name: "KotlinRuntimeSupport",
            dependencies: ["KotlinRuntimeSupportBridge", "KotlinRuntime"]
        ),
        .target(
            name: "KotlinRuntimeSupportBridge"
        ),
        .target(
            name: "KotlinCoroutineSupport",
            dependencies: ["KotlinRuntimeSupport", "KotlinCoroutineSupportBridge", "KotlinRuntime"]
        ),
        .target(
            name: "KotlinCoroutineSupportBridge"
        ),
        .target(
            name: "ReadingKit",
            dependencies: ["ExportedKotlinPackages", "KotlinRuntimeSupport", "KotlinCoroutineSupport", "OrgJetbrainsKotlinxKotlinxCoroutinesCore", "SharedBridge_ReadingKit", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_ReadingKit"
        ),
        .target(
            name: "OrgJetbrainsKotlinxKotlinxCoroutinesCore",
            dependencies: ["ExportedKotlinPackages", "KotlinRuntimeSupport", "KotlinCoroutineSupport", "SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore", "KotlinRuntime"]
        ),
        .target(
            name: "SharedBridge_OrgJetbrainsKotlinxKotlinxCoroutinesCore"
        ),
        .target(
            name: "KotlinRuntime"
        )
    ]
)
