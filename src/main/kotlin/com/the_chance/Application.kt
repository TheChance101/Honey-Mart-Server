package com.the_chance

import com.the_chance.di.myModule
import com.the_chance.plugins.configureMonitoring
import com.the_chance.plugins.configureRouting
import com.the_chance.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}


fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(myModule)
    }
    configureSerialization()
    configureMonitoring()
    configureRouting()
}


fun getDatabaseInstance(): Database {
    val host = System.getenv("host")
    val port = System.getenv("port")
    val databaseName = System.getenv("databaseName")
    val databaseUsername = System.getenv("databaseUsername")
    val databasePassword = System.getenv("databasePassword")

    return Database.connect(
        "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = databaseUsername,
        password = databasePassword
    )
}