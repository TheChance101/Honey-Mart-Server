package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.data.service.ProductService
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productsRoutes(productService: ProductService) {

    route("/product") {

        get("/{productId}") {
            val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val categories = productService.getAllCategoryForProduct(productId = productId)
                call.respond(ServerResponse.success(categories))
            }
        }

        post {
            val params = call.receiveParameters()
            val productName = params["name"]?.trim().orEmpty()
            val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
            val productQuantity = params["quantity"]?.trim()
            val categoriesId = params["categoriesId"]?.trim()?.toLongIds()

            handleException(call) {
                val newAddedProduct = productService.create(productName, productPrice, productQuantity, categoriesId)
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

                val isProductUpdated = productService.updateProduct(
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
                val updatedProductCategory = productService.updateProductCategory(
                    productId = productId, categoryIds = categoriesId
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success(updatedProductCategory))
            }
        }

        delete("{id}") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            handleException(call) {
                val result = productService.deleteProduct(productId = productId)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(result = result, successMessage = "Product Deleted successfully.")
                )
            }
        }
    }
}

