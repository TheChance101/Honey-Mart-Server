package com.the_chance


import com.the_chance.di.myModule
import com.thechance.api.plugins.configureMonitoring
import com.thechance.api.plugins.configureRouting
import com.thechance.api.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module).start(wait = true)
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