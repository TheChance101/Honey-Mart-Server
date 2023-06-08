val exposed_version : String by project
val koin_version:String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.postgresql:postgresql:42.6.0")

    implementation ("io.insert-koin:koin-ktor:$koin_version")
    implementation ("io.insert-koin:koin-logger-slf4j:$koin_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    implementation("org.mindrot:jbcrypt:0.4")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.6.10")
}
