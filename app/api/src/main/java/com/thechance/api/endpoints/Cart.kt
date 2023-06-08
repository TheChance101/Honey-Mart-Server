package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.data.usecase.cart.CartUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cartRoutes(cartUseCasesContainer: CartUseCasesContainer) {
    route("/cart") {

        get("/{userId}") {
            val userId = call.parameters["userId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val products = cartUseCasesContainer.getCartUseCase(userId)
                call.respond(ServerResponse.success(products))
            }
        }

        post("/addProduct") {
            handleException(call) {
                val params = call.receiveParameters()
                val userId = params["userId"]?.trim()?.toLongOrNull()
                val productId = params["productId"]?.trim()?.toLongOrNull()
                val count = params["count"]?.trim()?.toIntOrNull()
                cartUseCasesContainer.addProductToCartUseCase(userId = userId, productId = productId, count)
                call.respond(HttpStatusCode.Created, ServerResponse.success("Added successfully"))
            }
        }

        delete {
            handleException(call) {
                val params = call.receiveParameters()
                val userId = params["userId"]?.trim()?.toLongOrNull()
                val productId = params["productId"]?.trim()?.toLongOrNull()
                cartUseCasesContainer.deleteProductInCartUseCase(userId = userId, productId = productId)
                call.respond(HttpStatusCode.OK, ServerResponse.success("Deleted successfully"))
            }
        }
    }
}