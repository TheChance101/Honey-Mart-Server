package com.thechance.api.plugins

import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenVerifier
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val config: TokenConfig by inject()
    val verifier: TokenVerifier by inject()
    val jwtVerifier = verifier.getVerifier()

    authentication {
        jwt {
            realm = this@configureSecurity.environment.config.tryGetString("jwt.realm").toString()
            verifier(
                jwtVerifier
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

}