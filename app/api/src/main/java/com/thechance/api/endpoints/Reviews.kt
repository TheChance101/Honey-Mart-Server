package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiReviewsWithStatisticsModel
import com.thechance.core.domain.usecase.review.ReviewUseCaseContainer
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

fun Route.reviewRoutes() {
    val reviewUseCaseContainer: ReviewUseCaseContainer by inject()
    route("/reviews") {

        authenticate(API_KEY_AUTHENTICATION) {
            /**
             * get reviews for product by productId
             * */
            get("/{productId}") {
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val reviewsWithStatistics =
                    reviewUseCaseContainer.getProductReviewsUseCase(productId, page).toApiReviewsWithStatisticsModel()
                call.respond(HttpStatusCode.OK, ServerResponse.success(reviewsWithStatistics))
            }
        }
        authenticate(JWT_AUTHENTICATION) {

            /**
             * add new review
             * */
            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val params = call.receiveParameters()
                val productId = params["productId"]?.trim()?.toLongOrNull()
                val orderId = params["orderId"]?.trim()?.toLongOrNull()
                val content = params["content"]?.trim()
                val rating = params["rating"]?.trim()?.toIntOrNull()
                val isAdded = reviewUseCaseContainer.addProductReviewUseCase(
                    userId, productId, orderId, content, rating, role
                )
                call.respond(
                    HttpStatusCode.Created, ServerResponse.success(isAdded, "Review added successfully")
                )
            }
        }
    }
}