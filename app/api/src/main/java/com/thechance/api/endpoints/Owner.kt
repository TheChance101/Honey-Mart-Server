package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.domain.usecase.owner.OwnerUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.ownerRoutes() {

    val ownerUseCaseContainer: OwnerUseCaseContainer by inject()

    route("/owner") {

        post {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val password = params["password"]?.trim()

                val newUser = ownerUseCaseContainer.createOwnerUseCase(name, password)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newUser, "user created successfully"))
            }
        }

    }
}