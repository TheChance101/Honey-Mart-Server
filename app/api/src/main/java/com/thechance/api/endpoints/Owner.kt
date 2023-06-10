package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiOwnerModel
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

        post("/signup") {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val password = params["password"]?.trim()

                ownerUseCaseContainer.createOwnerUseCase(name, password)
                call.respond(HttpStatusCode.Created, ServerResponse.success("user created successfully"))
            }
        }

        post("/login") {
            handleException(call) {
                val params = call.receiveParameters()
                val name = params["username"]?.trim().toString()
                val password = params["password"]?.trim().toString()

                val token = ownerUseCaseContainer.verifyMarketOwnerUseCase(name, password)
                call.respond(HttpStatusCode.Created, ServerResponse.success(token, "Logged in Successfully"))
            }
        }
    }
}