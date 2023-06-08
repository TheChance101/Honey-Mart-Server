package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.data.usecase.cart.GetCartUseCase
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cartRoutes(getCartUseCase: GetCartUseCase) {
    route("/cart") {

        get("/{userId}") {
            val userId = call.parameters["userId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val products = getCartUseCase(userId)
                call.respond(ServerResponse.success(products))
            }
        }

    }
}