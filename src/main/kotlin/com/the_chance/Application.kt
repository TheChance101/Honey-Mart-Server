package com.the_chance


import com.the_chance.di.appModules
import com.thechance.api.plugins.*
import com.thechance.core.data.datasource.database.CoreDataBase
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }

    CoreDataBase()
//    configureFirebaseApp()
    configureSerialization()
    configureStatusExceptions()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}