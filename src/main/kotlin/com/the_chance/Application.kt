package com.the_chance


import com.the_chance.di.appModules
import com.thechance.api.plugins.configureMonitoring
import com.thechance.api.plugins.configureRouting
import com.thechance.api.plugins.configureSecurity
import com.thechance.api.plugins.configureSerialization
import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import java.util.concurrent.TimeUnit

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }

    val tokenConfig = TokenConfig(
        issuer = ApplicationConfig("jwt.issuer").toString(),
        audience = ApplicationConfig("jwt.audience").toString(),
        expiresIn = TimeUnit.HOURS.toMillis(1),
        secret = System.getenv("HONEY_JWT_SECRET")
    )

    CoreDataBase()
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting()
}