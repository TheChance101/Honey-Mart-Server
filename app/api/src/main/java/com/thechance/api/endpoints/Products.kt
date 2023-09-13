package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiProductModel
import com.thechance.api.model.mapper.toApiProductWithAverageRatingModel
import com.thechance.api.model.mapper.toApiProductWithReviews
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.domain.usecase.product.ProductUseCasesContainer
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

fun Route.productsRoutes() {

    val productUseCasesContainer: ProductUseCasesContainer by inject()

    route("/product") {

        authenticate(API_KEY_AUTHENTICATION) {
            get {
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val products = productUseCasesContainer.getAllProductsUseCase(page).map { it.toApiProductModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(products))
            }

            get("/{productId}/categories") {
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val categories =
                    productUseCasesContainer.getCategoriesForProductUseCase(productId = productId)
                        .map { it.toApiCategoryModel() }
                call.respond(ServerResponse.success(categories))
            }

            /**
             * get product details by productId
             */
            get("/{productId}") {
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val categories =
                    productUseCasesContainer.getProductDetailsUseCase(productId = productId)
                        .toApiProductWithReviews()
                call.respond(ServerResponse.success(categories))
            }

            get("/recentProducts") {
                val products =
                    productUseCasesContainer.getMostRecentProductsUseCase()
                        .map { it.toApiProductModel() }
                call.respond(ServerResponse.success(products))
            }
            get("/{productId}/similarProducts") {
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
                val products =
                    productUseCasesContainer.getSimilarProductsUseCase(productId, page)
                        .map { it.toApiProductWithAverageRatingModel() }
                call.respond(ServerResponse.success(products))
            }

            get("/search") {
                val param = call.request.queryParameters
                val query = param["query"]?.trim()
                val page = param["page"]?.toIntOrNull() ?: 1
                val sort = param["sort"]


                val searchResults =
                    productUseCasesContainer.searchProductsByNameUseCase(query, sort, page)
                        .map { it.toApiProductModel() }

                call.respond(ServerResponse.success(searchResults))
            }
        }

        authenticate(JWT_AUTHENTICATION) {

            post {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val params = call.receiveParameters()
                val productName = params["name"]?.trim().orEmpty()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
                val description = params["description"]?.trim()
                val categoriesId = params["categoriesId"]?.trim()?.toLongIds()

                val product = productUseCasesContainer.createProductUseCase(
                    productName, productPrice, description, categoriesId, marketOwnerId, role
                ).toApiProductModel()

                call.respond(HttpStatusCode.Created, ServerResponse.success(product))
            }

            put("{id}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                val params = call.receiveParameters()
                val productName = params["name"]?.trim()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull()
                val description = params["description"]?.trim()

                productUseCasesContainer.updateProductUseCase(
                    productId = productId,
                    productName = productName,
                    productPrice = productPrice,
                    description = description,
                    marketOwnerId, role
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))

            }

            put("{id}/updateCategories") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                val params = call.receiveParameters()
                val categoriesId = params["categoriesId"]?.trim().toLongIds()

                productUseCasesContainer.updateProductCategoryUseCase(
                    productId = productId,
                    categoryIds = categoriesId,
                    marketOwnerId, role
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))
            }

            delete("{id}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val productId = call.parameters["id"]?.trim()?.toLongOrNull()

                val result =
                    productUseCasesContainer.deleteProductUseCase(
                        productId = productId,
                        marketOwnerId,
                        role
                    )
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(
                        result = result,
                        successMessage = "Product Deleted successfully."
                    )
                )
            }

            post("/{productId}/uploadImages") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val imageParts = call.receiveMultipart()

                productUseCasesContainer.addImageProductUseCase(
                    marketOwnerId = marketOwnerId,
                    role = role,
                    productId = productId,
                    image = imageParts
                )
                call.respond(HttpStatusCode.Created, ServerResponse.success(true, "uploaded."))
            }

            put("/{productId}/updateImages") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
                val imageParts = call.receiveMultipart()

                productUseCasesContainer.updateProductImageUseCase(
                    marketOwnerId = marketOwnerId,
                    role = role,
                    productId = productId,
                    image = imageParts
                )
                call.respond(HttpStatusCode.Created, ServerResponse.success(true, "images updated."))
            }

            delete("{productId}/image/{imageId}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val imageId = call.parameters["imageId"]?.trim()?.toLongOrNull()
                val productId = call.parameters["productId"]?.trim()?.toLongOrNull()

                productUseCasesContainer.deleteImageFromProductUseCase(
                    productId = productId,
                    imageId = imageId,
                    role = role,
                    marketOwnerId = marketOwnerId
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success("Deleted successfully"))
            }

        }
    }
}

