package com.thechance.api.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thechance.core.data.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import java.util.concurrent.TimeUnit

fun Application.configureSecurity() {

    val config = TokenConfig(
        issuer = ApplicationConfig("jwt.issuer").toString(),
        audience = ApplicationConfig("jwt.audience").toString(),
        expiresIn = TimeUnit.HOURS.toMillis(1),
        secret = System.getenv("HONEY_JWT_SECRET")
    )

    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.tryGetString("jwt.realm").toString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

}