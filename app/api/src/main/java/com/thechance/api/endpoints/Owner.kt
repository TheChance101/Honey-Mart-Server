package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiOwnerModel
import com.thechance.api.model.mapper.toApiTokens
import com.thechance.core.domain.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.ownerRoutes() {

    val ownerUseCaseContainer: OwnerUseCaseContainer by inject()

    route("/owner") {
        authenticate(API_KEY_AUTHENTICATION) {
            post("/signup") {
                val params = call.receiveParameters()
                val name = params["fullName"]?.trim()
                val email = params["email"]?.trim()
                val password = params["password"]?.trim()

                ownerUseCaseContainer.createOwnerUseCase(fullName = name, password = password, email = email)
                call.respond(HttpStatusCode.Created, ServerResponse.success("user created successfully"))

            }

            post("/login") {
                val params = call.receiveParameters()
                val email = params["email"]?.trim().toString()
                val password = params["password"]?.trim().toString()

            val token = ownerUseCaseContainer.verifyMarketOwnerUseCase(email, password).toApiTokens()
            call.respond(HttpStatusCode.Created, ServerResponse.success(token, "Logged in Successfully"))
        }


        authenticate(JWT_AUTHENTICATION) {
            get("/Profile") {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val owner = ownerUseCaseContainer.getOwnerProfileUseCase(ownerId, role).toApiOwnerModel()
                call.respond(HttpStatusCode.Found, ServerResponse.success(owner))
            }
        }
    }

}