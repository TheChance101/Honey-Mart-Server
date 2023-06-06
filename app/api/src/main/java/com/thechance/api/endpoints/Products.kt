package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.data.usecase.product.ProductUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productsRoutes(productUseCasesContainer: ProductUseCasesContainer) {

    route("/product") {

        get("/{productId}") {
            val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val categories = productUseCasesContainer.getCategoriesForProductUseCase(productId = productId)
                call.respond(ServerResponse.success(categories))
            }
        }

        post {
            handleException(call) {
                val params = call.receiveParameters()
                val productName = params["name"]?.trim().orEmpty()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
                val productQuantity = params["quantity"]?.trim()
                val categoriesId = params["categoriesId"]?.trim()?.toLongIds()

                val newAddedProduct = productUseCasesContainer.createProductUseCase(
                    productName, productPrice, productQuantity, categoriesId
                )
                call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
            }
        }

        put("{id}") {
            handleException(call) {
                val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                val params = call.receiveParameters()
                val productName = params["name"]?.trim()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull()
                val productQuantity = params["quantity"]?.trim()


                val isProductUpdated = productUseCasesContainer.updateProductUseCase(
                    productId = productId,
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success(isProductUpdated))
            }
        }

        put("{id}/updateCategories") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            val params = call.receiveParameters()
            val categoriesId = params["categoriesId"]?.trim().toLongIds()

            handleException(call) {
                val updatedProductCategory = productUseCasesContainer.updateProductCategoryUseCase(
                    productId = productId, categoryIds = categoriesId
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success(updatedProductCategory))
            }
        }

        delete("{id}") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            handleException(call) {
                val result = productUseCasesContainer.deleteProductUseCase(productId = productId)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(result = result, successMessage = "Product Deleted successfully.")
                )

            }
        }
    }
}

