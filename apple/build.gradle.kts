// What an iOS developer gets from Kotlin: a small library with the shapes the reading list uses, exported twice,
// as an Objective-C framework (the classic way) and through Swift export (Kotlin 2.4, Alpha).
plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach {
        it.binaries.framework {
            baseName = "ReadingKit"      // the Objective-C export
            isStatic = true
        }
    }
    sourceSets {
        commonMain.dependencies { implementation(libs.kotlinx.coroutines.core) }
    }
    swiftExport {                        // the Swift export, of the same code
        moduleName = "ReadingKit"
        flattenPackage = "dev.fauzisho.readinglist.apple"
    }
}
