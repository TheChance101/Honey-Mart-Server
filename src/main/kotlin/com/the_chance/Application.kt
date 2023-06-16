package com.the_chance


import com.the_chance.di.appModules
import com.thechance.api.plugins.*
import com.thechance.core.data.datasource.database.CoreDataBase
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.util.concurrent.TimeUnit

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

//    install(CachingHeaders) { options { _, _ -> CachingOptions(CacheControl.MaxAge(1800)) } }

    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }

    CoreDataBase()
    configureSerialization()
    configureStatusExceptions()
    configureMonitoring()
    configureSecurity()
    configureRouting()
    configureCaching()
}
