package com.thechance.api.plugins

import com.auth0.jwt.exceptions.JWTDecodeException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.TokenVerifier
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.response.*
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
            challenge { _, _ ->
                // get custom error message if error exists
                val header = call.request.headers["Authorization"]
                header?.let {
                    if (it.isNotEmpty()) {
                        try {
                            if ((!it.contains("Bearer", true))) throw JWTDecodeException("")
                            val jwt = it.replace("Bearer ", "")
                            jwtVerifier.verify(jwt)
                            ""
                        } catch (e: TokenExpiredException) {
                            call.respond(
                                HttpStatusCode.Unauthorized,
                                "Authentication failed: Access token expired"
                            )
                        } catch (e: SignatureVerificationException) {
                            call.respond(
                                HttpStatusCode.BadRequest,
                                "Authentication failed: Failed to parse Access token"
                            )
                        } catch (e: JWTDecodeException) {
                            call.respond(
                                HttpStatusCode.BadRequest,
                                "Authentication failed: Failed to parse Access token"
                            )
                        }
                    } else call.respond(
                        HttpStatusCode.BadRequest,
                        "Authentication failed: Access token not found"
                    )
                } ?: call.respond(
                    HttpStatusCode.Unauthorized, "Authentication failed: No authorization header found"
                )
                call.respond("Unauthorized")
            }
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

}