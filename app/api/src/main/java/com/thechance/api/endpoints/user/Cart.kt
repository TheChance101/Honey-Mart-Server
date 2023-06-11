package com.thechance.api.endpoints.user

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCart
import com.thechance.api.utils.handleException
import com.thechance.core.domain.usecase.cart.CartUseCasesContainer
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.cartRoutes() {

    val cartUseCasesContainer: CartUseCasesContainer by inject()

    authenticate {
        route("/cart") {
            get {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val products = cartUseCasesContainer.getCartUseCase(userId, role = role).toApiCart()
                    call.respond(ServerResponse.success(products))
                }
            }

            post("/addProduct") {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val params = call.receiveParameters()
                    val productId = params["productId"]?.trim()?.toLongOrNull()
                    val count = params["count"]?.trim()?.toIntOrNull()
                    cartUseCasesContainer.addProductToCartUseCase(userId = userId, productId = productId, count, role)
                    call.respond(HttpStatusCode.Created, ServerResponse.success("Added successfully"))
                }
            }

            delete {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val params = call.receiveParameters()
                    val productId = params["productId"]?.trim()?.toLongOrNull()
                    cartUseCasesContainer.deleteProductInCartUseCase(userId = userId, productId = productId, role)
                    call.respond(HttpStatusCode.OK, ServerResponse.success("Deleted successfully"))
                }
            }
        }
    }
}