package com.the_chance.endpoints

import com.the_chance.data.services.ProductService
import com.the_chance.data.ServerResponse
import com.the_chance.utils.orZero
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

    post("/product") {
        val params = call.receiveParameters()
        val productName = params["name"]?.trim().orEmpty()
        val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
        val productQuantity = params["quantity"]?.trim().orEmpty()

        if (productName.length < 4) {
            call.respond(ServerResponse.error("Product name length should be 4 characters or more"))
        } else if (productPrice < 0.0) {
            call.respond(ServerResponse.error("Product Price should be positive number."))
        } else {
            val newAddedProduct = productService.create(productName, productPrice, productQuantity)
            call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
        }
    }

    post("/product/{id}") {
        val productId = call.parameters["id"]?.trim().orEmpty()

        val params = call.receiveParameters()
        val productName = params["name"]?.trim().orEmpty()
        val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
        val productQuantity = params["quantity"]?.trim().orEmpty()

        if (productId.length < 4) {
            call.respond(ServerResponse.error("Not valid Product Id"))
        } else if (!productService.isValid(
                productName = productName,
                productPrice = productPrice,
                productQuantity = productQuantity
            )
        ) {
            call.respond(ServerResponse.error("Not valid Request."))
        } else {
            val updatedProduct = productService.updateProduct(
                productId = productId,
                productName = productName,
                productPrice = productPrice,
                productQuantity = productQuantity
            )
            call.respond(HttpStatusCode.Accepted, ServerResponse.success(updatedProduct))
        }
    }

    delete("/product/{id}") {
        val productId = call.parameters["id"]?.trim().orEmpty()
        if (productId.length < 4) {
            call.respond(ServerResponse.error("Not valid Product Id"))
        } else {
            val deletedProduct = productService.deleteProduct(productId = productId)
            call.respond(HttpStatusCode.Accepted, ServerResponse.success(deletedProduct))
        }
    }

}