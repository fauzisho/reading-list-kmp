import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

// What the app and the server share: the sync format, how two versions of a book merge, and the clock.
// No UI and no database: it compiles for every target the app has, and for the server's JVM.
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.kotlinSerialization)
}

val iosEnabled = providers.gradleProperty("kmp.ios").orNull == "true"

kotlin {
    android {
        namespace = "dev.fauzisho.readinglist.sync"
        compileSdk = 37
        minSdk = 24
    }
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser() }
    if (iosEnabled) {
        iosArm64()
        iosSimulatorArm64()
    }
    sourceSets {
        commonMain.dependencies { implementation(libs.kotlinx.serialization.json) }
        commonTest.dependencies { implementation(libs.kotlin.test) }
    }
}
