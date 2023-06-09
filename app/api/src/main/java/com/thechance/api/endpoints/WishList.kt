package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.data.usecase.wishlist.WishListUseCaseContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.wishListRoutes(wishListUseCaseContainer: WishListUseCaseContainer) {
    route("/wishList") {

        post {
            handleException(call) {
                val params = call.receiveParameters()
                val productId = params["productId"]?.trim()?.toLongOrNull()
                val userId = params["userId"]?.trim()?.toLongOrNull()

                val newAddedWishList = wishListUseCaseContainer.createWishListUseCase(
                    userId = userId,
                    productId = productId
                )
                call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedWishList))
            }
        }
    }
}