package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiMarketOrder
import com.thechance.api.model.mapper.toApiMarketOrders
import com.thechance.api.model.mapper.toApiUserOrders
import com.thechance.core.domain.usecase.order.OrderUseCasesContainer
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.orderRoutes() {

    val orderUseCasesContainer: OrderUseCasesContainer by inject()

    authenticate(JWT_AUTHENTICATION) {
        route("/order") {

            get("/marketOrders") {
                val params = call.request.queryParameters
                val orderState = params["orderState"]?.trim()?.toIntOrNull()
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val orders =
                    orderUseCasesContainer.getOrdersForMarketUseCase(ownerId, role, orderState).toApiMarketOrders()
                call.respond(ServerResponse.success(orders))

            }

            get("/userOrders") {
                val params = call.request.queryParameters
                val orderState = params["orderState"]?.trim()?.toIntOrNull()
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val orders =
                    orderUseCasesContainer.getOrdersForUserUseCase(userId, role, orderState).toApiUserOrders()
                call.respond(ServerResponse.success(orders))

            }

            /**
             * get Order Details
             * */
            get("/{id}") {
                val orderId = call.parameters["id"]?.trim()?.toLongOrNull()
                val orders =
                    orderUseCasesContainer.getOrderDetailsUseCase(orderId).toApiMarketOrder()
                call.respond(ServerResponse.success(orders))

            }

            post("/checkout") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val isCreated = orderUseCasesContainer.createOrderUseCase(userId, role)
                call.respond(ServerResponse.success(isCreated, "Checkout Success"))

            }

            /**
             * Update order state
             */
            put("/{id}") {
                val params = call.receiveParameters()
                val orderId = call.parameters["id"]?.trim()?.toLongOrNull()
                val orderState = params["state"]?.toIntOrNull()

                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val updatedStatus = orderUseCasesContainer.updateOrderStateUseCase(orderId, orderState, role)
                call.respond(ServerResponse.success(updatedStatus))

            }
        }
    }
}