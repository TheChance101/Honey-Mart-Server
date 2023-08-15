package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCoupon
import com.thechance.api.model.mapper.toApiUserCoupon
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

            //get all valid coupons
            get("/allValidCoupons") {
                val coupons =
                    couponUseCaseContainer.getValidCouponsUseCase()
                        .map { it.toApiCoupon() }
                call.respond(ServerResponse.success(coupons))
            }

            //get all coupons that not clipped from a specific user
            get("/allUserCoupons") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val coupons =
                    couponUseCaseContainer.getAllCouponsForUserUseCase(userId, role)
                        .map { it.toApiUserCoupon() }
                call.respond(ServerResponse.success(coupons))
            }
            //get all coupons that not clipped from a specific user
            get("/allClippedUserCoupons") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val coupons =
                    couponUseCaseContainer.getAllClippedCouponsForUser(userId, role)
                        .map { it.toApiUserCoupon() }
                call.respond(ServerResponse.success(coupons))
            }

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