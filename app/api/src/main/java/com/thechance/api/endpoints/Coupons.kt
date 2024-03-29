package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCoupon
import com.thechance.api.model.mapper.toApiMarketCoupon
import com.thechance.api.model.mapper.toApiProductModel
import com.thechance.api.model.mapper.toApiUserCoupon
import com.thechance.core.domain.usecase.coupon.CouponUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
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
        authenticate(API_KEY_AUTHENTICATION) {
            //get all valid coupons
            get("/allValidCoupons") {
                val coupons =
                    couponUseCaseContainer.getValidCouponsUseCase()
                        .map { it.toApiCoupon() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(coupons))
            }
        }
        authenticate(JWT_AUTHENTICATION) {

            //get all coupons that not clipped from a specific user
            get("/allUserCoupons") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val coupons =
                    couponUseCaseContainer.getAllCouponsForUserUseCase(userId, role)
                        .map { it.toApiUserCoupon() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(coupons))
            }
            //get all coupons that not clipped from a specific user
            get("/allClippedUserCoupons") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val coupons =
                    couponUseCaseContainer.getAllClippedCouponsForUserUseCase(userId, role)
                        .map { it.toApiUserCoupon() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(coupons))
            }
            //get all coupons for specific market products
            get("/allMarketCoupons") {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val coupons =
                    couponUseCaseContainer.getAllCouponsForMarketUseCase(ownerId, role)
                        .map { it.toApiMarketCoupon() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(coupons))
            }
            //get all market products without valid coupons
            get("/marketProducts") {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val products =
                    couponUseCaseContainer.getMarketProductsWithoutValidCouponsUseCase(ownerId, role)
                        .map { it.toApiProductModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(products))
            }
            //search market products without valid coupons
            get("/searchMarketProducts") {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val param = call.request.queryParameters
                val query = param["query"]?.trim()
                val products =
                    couponUseCaseContainer.searchMarketProductsWithoutValidCouponsUseCase(ownerId, role, query)
                        .map { it.toApiProductModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(products))
            }

            //clip coupon for user
            put("/clip/{id}") {
                val couponId = call.parameters["id"]?.trim()?.toLongOrNull()

                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val isClipped = couponUseCaseContainer.clipCouponUseCase(userId, role, couponId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(isClipped))

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