package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.data.usecase.user.UserUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userUseCasesContainer: UserUseCaseContainer) {

    route("/user") {

        post {
            handleException(call){
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val password = params["password"]?.trim()

                val newUser = userUseCasesContainer.createUserUseCase(name, password)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newUser, "user created successfully"))
            }
        }

    }
}