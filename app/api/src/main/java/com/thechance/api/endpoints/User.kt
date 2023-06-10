package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.domain.usecase.user.UserUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {

    val userUseCasesContainer: UserUseCaseContainer by inject()

    route("/user") {

        post("/signup") {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["username"]?.trim()
                val password = params["password"]?.trim()
                val fullName = params["fullName"]?.trim()
                val email = params["email"]?.trim()

                val newUser =
                    userUseCasesContainer.createUserUseCase(name, password, fullName = fullName, email = email)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newUser, "user created successfully"))
            }
        }

        post("/login") {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["username"]?.trim().toString()
                val password = params["password"]?.trim().toString()

                val token = userUseCasesContainer.verifyUserUseCase(name, password)
                call.respond(HttpStatusCode.Created, ServerResponse.success(token, "Logged in Successfully"))
            }
        }
    }
}
