package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiOrderModel
import com.thechance.api.utils.handleException
import com.thechance.core.domain.usecase.order.OrderUseCasesContainer
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
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
            get("/{marketId}") {
                handleException(call) {
                    val marketId = call.parameters["marketId"]?.trim()?.toLongOrNull()
                    val principal = call.principal<JWTPrincipal>()
                    val role = principal?.getClaim(ROLE_TYPE, String::class)

                    val orders =
                        orderUseCasesContainer.getOrdersForMarketUseCase(marketId, role).map { it.toApiOrderModel() }
                    call.respond(ServerResponse.success(orders))
                }
            }

            get("/{userId}") {
                handleException(call) {
                    val userId = call.parameters["userId"]?.trim()?.toLongOrNull()
                    val principal = call.principal<JWTPrincipal>()
                    val role = principal?.getClaim(ROLE_TYPE, String::class)

                    val orders =
                        orderUseCasesContainer.getOrdersForUserUseCase(userId, role).map { it.toApiOrderModel() }
                    call.respond(ServerResponse.success(orders))
                }
            }
            /**
             * create new order
             **/
            post {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject?.toLongOrNull()
                    val isAdded = orderUseCasesContainer.createOrderUseCase(userId)

                    call.respond(HttpStatusCode.Created, ServerResponse.success(isAdded))
                }
            }

        }
    }
}