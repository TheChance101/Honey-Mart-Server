package com.the_chance


import com.the_chance.di.appModules
import com.thechance.api.plugins.configureMonitoring
import com.thechance.api.plugins.configureRouting
import com.thechance.api.plugins.configureSecurity
import com.thechance.api.plugins.configureSerialization
import com.thechance.api.security.JwtTokenService
import com.thechance.api.security.TokenConfig
import com.thechance.core.data.database.CoreDataBase
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
    configureSerialization()
    configureMonitoring()
    configureRouting()


    val tokenConfig = TokenConfig(
        issuer = "http://0.0.0.0:8080",
        audience =  "users",
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret =  "JWT_SECRET"
    )
    configureSecurity(tokenConfig)
}