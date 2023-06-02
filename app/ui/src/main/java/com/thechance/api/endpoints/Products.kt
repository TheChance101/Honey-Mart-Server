package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.service.ProductService
import com.thechance.api.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productsRoutes(productService: ProductService) {

    get("/products") {
        val products = productService.getAllProducts()
        call.respond(ServerResponse.success(products))
    }

    route("/product") {

        get("/{productId}") {
            val productId = call.parameters["productId"]?.trim()?.toLongOrNull()
            try {
                val products = productService.getAllCategoryForProduct(productId = productId)
                call.respond(ServerResponse.success(products))
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
            }
        }

        post {
            try {
                val params = call.receiveParameters()
                val productName = params["name"]?.trim().orEmpty()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
                val productQuantity = params["quantity"]?.trim()
                val categoriesId = params["categoriesId"]?.trim()?.split(",")?.map { it.toLongOrNull() ?: 0L }

                val newAddedProduct = productService.create(productName, productPrice, productQuantity, categoriesId)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
            } catch (e: InvalidInputException) {
                val error = e.message.toString()
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(error))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }

        put("{id}") {
            try {
                val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                val params = call.receiveParameters()
                val productName = params["name"]?.trim()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull()
                val productQuantity = params["quantity"]?.trim()

                val updatedProduct = productService.updateProduct(
                    productId = productId,
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success(true, updatedProduct))
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(e.message.toString()))
            } catch (e: ItemNotAvailableException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }

        put("{id}/updateCategories") {
            try {
                val productId = call.parameters["id"]?.trim()?.toLongOrNull()
                val params = call.receiveParameters()
                val categoriesId = params["categoriesId"]?.trim().toLongIds()

                val updatedProductCategory = productService.updateProductCategory(
                    productId = productId, categoryIds = categoriesId
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success(updatedProductCategory))
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(e.message.toString()))
            }
        }

        delete("{id}") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            try {
                val deletedProduct = productService.deleteProduct(productId = productId)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(result = true, successMessage = deletedProduct)
                )
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(e.message.toString()))
            } catch (e: ItemNotAvailableException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }
    }
}

