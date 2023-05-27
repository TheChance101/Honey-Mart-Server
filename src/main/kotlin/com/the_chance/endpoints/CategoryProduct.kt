package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.CategoryProductService
import com.the_chance.data.services.ProductService
import com.the_chance.data.services.validation.Error
import com.the_chance.utils.errorHandler
import com.the_chance.utils.orZero
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.categoryProductRoutes(categoryProductService: CategoryProductService) {

    route("/categoryProduct") {

        get("/{categoryId}") {
            val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
            try {
                val products = categoryProductService.getProductsFromCategory(categoryId = categoryId)
                call.respond(ServerResponse.success(products))
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
            }
        }

        post {
            try {
                val params = call.receiveParameters()
                val categoryId = params["categoryId"]?.trim()?.toLongOrNull()
                val productId = params["productId"]?.trim()?.toLongOrNull()

                val newRelation = categoryProductService.create(categoryId = categoryId, productId = productId)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newRelation))
            } catch (t: Throwable) {
                call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
            }
        }

    }
}
