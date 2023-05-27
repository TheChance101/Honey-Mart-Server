package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.ProductService
import com.the_chance.data.services.validation.Error
import com.the_chance.utils.errorHandler
import com.the_chance.utils.orZero
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productsRoutes(productService: ProductService) {

    route("/products") {

        get {
            val products = productService.getAllProducts()
            call.respond(ServerResponse.success(products))
        }

        post {
            try {
                val params = call.receiveParameters()
                val productName = params["name"]?.trim().orEmpty()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
                val productQuantity = params["quantity"]?.trim()

                val newAddedProduct = productService.create(productName, productPrice, productQuantity)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
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
            } catch (t: Error) {
                t.errorHandler(call)
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
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
            } catch (t: Error) {
                t.errorHandler(call)
            }
        }
    }
}

