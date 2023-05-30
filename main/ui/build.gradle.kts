
val ktor_version: String by project
val koin_version:String by project
val swagger_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("application")
}

group = "com.the_chance"
version = "0.0.1"

repositories {
    mavenCentral()
    gradlePluginPortal()
}


dependencies {

    api(project(":main:model"))

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:$swagger_version")

    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")

    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")

}