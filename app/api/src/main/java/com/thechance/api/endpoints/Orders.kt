package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.CreateOrderRequest
import com.thechance.api.utils.handleException
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toOrderItems
import com.thechance.core.data.usecase.order.OrderUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRoutes(orderUseCasesContainer: OrderUseCasesContainer) {
    route("/order") {
        /**
         * get Orders by market
         * */
        get {
            handleException(call) {
                val params = call.request.queryParameters
                val marketId = params["marketId"]?.trim()?.toLongOrNull()
                val orders = orderUseCasesContainer.getOrdersForMarketUseCase(marketId)
                call.respond(ServerResponse.success(orders))
            }
        }
        /**
         * create new order
         **/
        post {
            handleException(call) {
                val request = call.receive<CreateOrderRequest>()
                val marketId = request.marketId?.trim()?.toLongOrNull()
                val orderDate = request.orderDate?.trim().orEmpty()
                val totalPrice = request.totalPrice?.trim()?.toDoubleOrNull().orZero()
                val isPaid = request.isPaid?.trim().toBoolean()
                val orderItems = request.products?.toOrderItems()
                val newAddedOrder = orderUseCasesContainer.createOrderUseCase(
                    marketId, orderDate, totalPrice, isPaid, orderItems
                )
                call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedOrder))
            }
        }
    }
}