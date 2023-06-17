package com.thechance.api.endpoints.user

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiProductInWishListModel
import com.thechance.core.domain.usecase.wishlist.WishListUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.wishListRoutes() {

    val wishListUseCaseContainer: WishListUseCaseContainer by inject()

    authenticate {
        route("/wishList") {

            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()

                val params = call.receiveParameters()
                val productId = params["productId"]?.trim()?.toLongOrNull()

                wishListUseCaseContainer.addProductToWishListUseCase(userId = userId, productId = productId)

                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success("Added To WishList successfully \uD83E\uDD73")
                )

            }
            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()

                val wishList =
                    wishListUseCaseContainer.getWishListUseCase(userId).map { it.toApiProductInWishListModel() }

                call.respond(HttpStatusCode.OK, ServerResponse.success(wishList))
            }

            delete("{productId}") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()

                wishListUseCaseContainer.deleteProductFromWishListUseCase(userId, productId)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success("Deleted From WishList successfully \uD83D\uDE25\u200F ")
                )
            }
        }
    }
}