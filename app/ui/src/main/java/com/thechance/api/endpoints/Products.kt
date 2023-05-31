package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.service.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.xml.sax.ErrorHandler

fun Route.productsRoutes(productService: ProductService) {

    get("/products") {
        val products = productService.getAllProducts()
        call.respond(ServerResponse.success(products))
    }

    route("/product") {

        post {
            try {
                val params = call.receiveParameters()
                val productName = params["name"]?.trim().orEmpty()
                val productPrice = params["price"]?.trim()?.toDoubleOrNull() ?: 0.0
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
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.handleError(e))
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
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.handleError(e))
            }
        }
    }
}

