package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.mapper.toApiOrderModel
import com.thechance.api.model.mapper.toApiOrderModel
import com.thechance.api.utils.handleException
import com.thechance.core.domain.usecase.order.OrderUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.orderRoutes() {

    val orderUseCasesContainer: OrderUseCasesContainer by inject()

    route("/order") {
        /**
         * get Orders by market
         * */
        get {

            val params = call.request.queryParameters
            val marketId = params["marketId"]?.trim()?.toLongOrNull()
            val orders = orderUseCasesContainer.getOrdersForMarketUseCase(marketId).map { it.toApiOrderModel() }
            call.respond(ServerResponse.success(orders))

        }
        /**
         * create new order
         **/
        authenticate {
            post {

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", Long::class)
                val isAdded = orderUseCasesContainer.createOrderUseCase(userId)
                call.respond(HttpStatusCode.Created, ServerResponse.success(isAdded))

            }
        }
        /**
         * cancel order
         */
        put("/cancel/{orderId}") {

            val orderId = call.parameters["orderId"]?.trim()?.toLongOrNull()
            orderUseCasesContainer.cancelOrderUseCase(orderId)
            call.respond(HttpStatusCode.OK, ServerResponse.success("Canceled Successfully"))

        }
    }
}