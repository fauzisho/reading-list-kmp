import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidKmpLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room3)
}

val iosEnabled = providers.gradleProperty("kmp.ios").orNull == "true"

kotlin {
    compilerOptions {
        // Room's @ConstructedBy needs an expect object, whose actuals Room generates; expect/actual classes are Beta
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    android {
        namespace = "dev.fauzisho.readinglist.shared"
        compileSdk = 37
        minSdk = 24
        // Off by default in AGP's KMP library plugin; without it the APK has no Compose resources and the app crashes on start
        androidResources { enable = true }
        // commonTest also runs on a device or emulator, as instrumented tests (host tests cannot: Room's Android
        // drivers need a real Android). Run with ./gradlew :shared:connectedAndroidDeviceTest
        withDeviceTestBuilder { sourceSetTreeName = "test" }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    jvm()
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            commonWebpackConfig { outputFileName = "readinglist.js" }
            testTask { useKarma { useChromeHeadless() } }   // Compose needs Skia, which needs a browser (timeout: karma.config.d)
        }
        binaries.executable()
    }
    if (iosEnabled) {
        listOf(iosArm64(), iosSimulatorArm64()).forEach {
            it.binaries.framework {
                baseName = "Shared"
                isStatic = true
            }
        }
    }

    // The default hierarchy (commonMain → appleMain → iosMain, …) plus one group of our own:
    // nonAndroidMain, compiled for every target except Android.
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
        common {
            group("nonAndroid") {
                withJvm()
                withWasmJs()
                withIos()
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.room3.runtime)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.lifecycle.viewmodel.navigation3)
            implementation(libs.navigation3.ui)
            implementation(libs.adaptive.navigation3)
            implementation(libs.compose.components.resources)
            implementation(project(":sync"))              // the sync format and merge rules, shared with the server
            implementation(libs.sqlite.async)             // executeSQL in the hand-written migration, on every target
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        // Android: the SQLite that ships with Android (no native library in the APK; version 3.9 on API 24 to 3.44 on API 35)
        androidMain.dependencies {
            implementation(libs.sqlite.framework)
            implementation(libs.ktor.client.okhttp)
        }
        // desktop and iOS: SQLite compiled into the app, the same version everywhere; there is no such build for the browser
        jvmMain.dependencies {
            implementation(libs.sqlite.bundled)
            implementation(libs.ktor.client.java)
        }
        jvmTest.dependencies {
            implementation(libs.room3.testing)        // MigrationTestHelper
            implementation(libs.compose.ui.test)      // renders App() offscreen with Skia, for UiMeasureTest
            implementation(project(":server"))        // the real server, in-process, for the end-to-end sync tests
            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.netty)
        }
        getByName("androidDeviceTest").dependencies {
            implementation(libs.androidx.test.runner)
            implementation(libs.androidx.test.core)
            implementation(libs.androidx.test.junit)
        }
        if (iosEnabled) iosMain.dependencies {
            implementation(libs.sqlite.bundled)
            implementation(libs.ktor.client.darwin)
        }
        // in the browser: a web worker running SQLite's own WebAssembly build (sqlite-worker/)
        wasmJsMain.dependencies {
            implementation(libs.sqlite.web)
            implementation(libs.ktor.client.js)
            implementation(npm("reading-list-sqlite-worker", layout.projectDirectory.dir("sqlite-worker").asFile))
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

tasks.matching { it.name.startsWith("wasmJsBrowser") && it.name.endsWith("Webpack") }.configureEach {
    inputs.dir("sqlite-worker").withPropertyName("sqliteWorker")   // see the root build file
}

// Compose's resource plugin leaves this task unconfigured for device tests ("Value not set"); the tests use no resources
tasks.matching { it.name == "copyAndroidDeviceTestComposeResourcesToAndroidAssets" }.configureEach { enabled = false }

// Room generates the database code for each target with KSP
dependencies {
    for (target in listOf("Android", "Jvm", "WasmJs") + if (iosEnabled) listOf("IosArm64", "IosSimulatorArm64") else emptyList()) {
        add("ksp$target", libs.room3.compiler)
    }
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

compose.resources {
    packageOfResClass = "dev.fauzisho.readinglist.resources"
    publicResClass = false
}

compose.desktop {
    application {
        mainClass = "dev.fauzisho.readinglist.MainKt"
    }
}
