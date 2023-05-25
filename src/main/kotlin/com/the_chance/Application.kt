package com.the_chance

import com.the_chance.data.services.MarketService
import com.the_chance.data.services.ProductService
import com.the_chance.plugins.configureMonitoring
import com.the_chance.plugins.configureRouting
import com.the_chance.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    //environment
    val host = System.getenv("host")
    val port = System.getenv("port")
    val databaseName = System.getenv("databaseName")
    val databaseUsername = System.getenv("databaseUsername")
    val databasePassword = System.getenv("databasePassword")

    val database = Database.connect(
        "jdbc:postgresql://$host:$port/$databaseName",
        driver = "org.postgresql.Driver",
        user = databaseUsername,
        password = databasePassword
    )

    val productService = ProductService(database)
    val marketService = MarketService(database)
    configureSerialization()
    configureMonitoring()
    configureRouting(productService, marketService)
}
