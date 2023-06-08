package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.security.TokenClaim
import com.thechance.api.security.TokenConfig
import com.thechance.api.security.TokenService
import com.thechance.api.utils.handleException
import com.thechance.core.data.model.AuthResponse
import com.thechance.core.data.usecase.user.UserUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes(userUseCasesContainer: UserUseCaseContainer) {

    val tokenService: TokenService by inject()

    route("/user") {

        post {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val password = params["password"]?.trim()

                val newUser = userUseCasesContainer.createUserUseCase(name, password)

                call.respond(HttpStatusCode.Created, ServerResponse.success(newUser, "user created successfully"))
            }
        }

        post("login") {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val password = params["password"]?.trim()

                val tokenConfig = TokenConfig(
                    issuer = "http://0.0.0.0:8080",
                    audience =  "users",
                    expiresIn = 365L * 1000L * 60L * 60L * 24L,
                    secret =  "JWT_SECRET"
                )

                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(name = "userId", value = "1")
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = AuthResponse(token = token)
                )

                call.respond(HttpStatusCode.Created, ServerResponse.success(name, "user created successfully"))
            }
        }

    }
}