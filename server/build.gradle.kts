// A small sync server for the reading list: Ktor on the JVM, with the merge rules from :sync.
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

application {
    mainClass = "dev.fauzisho.readinglist.server.ServerKt"
}

dependencies {
    implementation(project(":sync"))
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.serialization.json)
    implementation(libs.logback)
}
