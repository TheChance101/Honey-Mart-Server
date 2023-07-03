package com.thechance.api.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thechance.core.data.security.principal.AppPrincipal
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.utils.*
import dev.forst.ktor.apikey.apiKey
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
    val apiKey = System.getenv(API_SECRET_KEY)

    install(Authentication) {

        apiKey(API_KEY_AUTHENTICATION) {
            headerName = API_KEY_HEADER_NAME

            challenge {
                throw InvalidApiKeyException()
            }

            validate { keyFromHeader ->
                keyFromHeader.takeIf { it == apiKey }?.let { AppPrincipal(it) }
            }
        }

        jwt(JWT_AUTHENTICATION) {
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
