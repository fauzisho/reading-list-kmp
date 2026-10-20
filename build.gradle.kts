plugins {
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room3) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.androidKmpLibrary) apply false
    alias(libs.plugins.androidApplication) apply false
}

// Gradle has yarn copy the local npm package shared/sqlite-worker into build/wasm/node_modules once, and neither
// yarn nor webpack notices later edits to it. Make both depend on the worker's files, and make yarn copy it again.
// (The two files are resolved while configuring, so that the task action holds plain Files and no reference to this
// script, which the configuration cache cannot store.)
val workerCopy = layout.buildDirectory.dir("wasm/node_modules/reading-list-sqlite-worker").get().asFile
val yarnIntegrity = layout.buildDirectory.file("wasm/node_modules/.yarn-integrity").get().asFile
tasks.matching { it.name == "kotlinWasmNpmInstall" }.configureEach {
    inputs.dir("shared/sqlite-worker").withPropertyName("sqliteWorker")
    val copy = workerCopy        // locals: the action must not reach back into the script
    val integrity = yarnIntegrity
    doFirst {
        copy.deleteRecursively()
        integrity.delete()
    }
}
