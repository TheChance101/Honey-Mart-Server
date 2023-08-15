package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.domain.usecase.coupon.CouponUseCaseContainer
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.couponRoutes() {
    val couponUseCaseContainer: CouponUseCaseContainer by inject()
    route("/coupon") {
        authenticate(JWT_AUTHENTICATION) {
            //add new coupon
            post {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val productId = params["productId"]?.toLongOrNull()
                val count = params["count"]?.toIntOrNull()
                val discountPercentage = params["discountPercentage"]?.toDoubleOrNull()
                val expirationDate = params["expirationDate"]?.trim().orEmpty()

                couponUseCaseContainer.createCouponUseCase(
                    ownerId = marketOwnerId,
                    role = role,
                    productId = productId,
                    count = count,
                    discountPercentage = discountPercentage,
                    expirationDate = expirationDate
                )

                call.respond(HttpStatusCode.Created, ServerResponse.success(true, "Coupon added successfully"))
            }
        }
    }
}