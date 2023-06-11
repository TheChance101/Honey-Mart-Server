package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiProductModel
import com.thechance.api.utils.handleException
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.domain.usecase.product.ProductUseCasesContainer
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

        get("/{productId}") {
            val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val categories = productUseCasesContainer.getCategoriesForProductUseCase(productId = productId)
                    .map { it.toApiCategoryModel() }
                call.respond(ServerResponse.success(categories))
            }
        }

        authenticate {

            post {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val params = call.receiveParameters()
                    val productName = params["name"]?.trim().orEmpty()
                    val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
                    val productQuantity = params["quantity"]?.trim()
                    val categoriesId = params["categoriesId"]?.trim()?.toLongIds()

                    val newAddedProduct = productUseCasesContainer.createProductUseCase(
                        productName, productPrice, productQuantity, categoriesId, marketOwnerId, role
                    ).toApiProductModel()
                    call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
                }
            }

            put("{id}") {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                    val params = call.receiveParameters()
                    val productName = params["name"]?.trim()
                    val productPrice = params["price"]?.trim()?.toDoubleOrNull()
                    val productQuantity = params["quantity"]?.trim()

                    productUseCasesContainer.updateProductUseCase(
                        productId = productId,
                        productName = productName,
                        productPrice = productPrice,
                        productQuantity = productQuantity,
                        marketOwnerId, role
                    )
                    call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))
                }
            }

            put("{id}/updateCategories") {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

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
            }

            delete("{id}") {
                handleException(call) {
                    val principal = call.principal<JWTPrincipal>()
                    val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                    val role = principal?.getClaim(ROLE_TYPE,String::class)

                    val productId = call.parameters["id"]?.trim()?.toLongOrNull()

                    val result =
                        productUseCasesContainer.deleteProductUseCase(productId = productId, marketOwnerId, role)
                    call.respond(
                        HttpStatusCode.OK,
                        ServerResponse.success(result = result, successMessage = "Product Deleted successfully.")
                    )

                }
            }
        }
    }
}

