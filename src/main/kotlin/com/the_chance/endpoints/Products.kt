package com.the_chance.endpoints

import com.the_chance.data.services.ProductService
import com.the_chance.data.ServerResponse
import com.the_chance.utils.orZero
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productsRoutes(productService: ProductService){

    get("/products"){
        val products = productService.getAllProducts()
        call.respond(ServerResponse.success(products))
    }

    post("/products") {
        val params = call.receiveParameters()
        val productName = params["name"]?.trim().orEmpty()
        val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()

        if (productName.length < 4){
            call.respond(ServerResponse.error("Product name length should be 4 characters or more"))
        } else {
            val newAddedProduct = productService.create(productName, productPrice)
            call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
        }
    }
}