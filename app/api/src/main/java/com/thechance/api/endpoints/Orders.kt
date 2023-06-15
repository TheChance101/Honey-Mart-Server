package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiOrderModel
import com.thechance.core.domain.usecase.order.OrderUseCasesContainer
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

    authenticate {
        route("/order") {
            /**
             * get Orders by market
             * */
            get("/marketorders") {
                val params = call.request.queryParameters
                val marketId = params["id"]?.trim()?.toLongOrNull()
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val orders =
                    orderUseCasesContainer.getOrdersForMarketUseCase(marketId, role).map { it.toApiOrderModel() }
                call.respond(ServerResponse.success(orders))

            }

            /**
             * get Orders by user
             * */
            get("/userorders") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val orders =
                    orderUseCasesContainer.getOrdersForUserUseCase(userId, role).map { it.toApiOrderModel() }
                call.respond(ServerResponse.success(orders))

            }

            /**
             * get Order Details
             * */
            get("{id}") {
                val orderId = call.parameters["id"]?.trim()?.toLongOrNull()
                val orders =
                    orderUseCasesContainer.getOrderDetailsUseCase(orderId).toApiOrderModel()
                call.respond(ServerResponse.success(orders))

            }

            /**
             * create order
             * */
            post("/createorder") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val isCreated = orderUseCasesContainer.createOrderUseCase(userId, role)
                call.respond(ServerResponse.success(isCreated))

            }
            /**
             * update order
             **/
            put("/updateorder/{id}") {
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