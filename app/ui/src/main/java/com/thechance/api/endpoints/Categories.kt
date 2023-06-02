package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.service.CategoryService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(categoryService: CategoryService) {

    /**
     * get all products in category
     * */
    get("/category/{categoryId}") {
        val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
        try {
            val products = categoryService.getProductsFromCategory(categoryId = categoryId)
            call.respond(ServerResponse.success(products))
        } catch (e: InvalidInputException) {
            call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(e.message.toString()))
        } catch (e: IdNotFoundException) {
            call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
        } catch (t: Exception) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
        }
    }

    post("/category") {
        try {
            val params = call.receiveParameters()
            val categoryName = params["name"]?.trim().orEmpty()
            val marketId = params["marketId"]?.toLongOrNull() ?: -1
            val imageId = params["imageId"]?.toIntOrNull() ?: -1

            val newCategory = categoryService.create(categoryName, marketId, imageId)
            call.respond(HttpStatusCode.Created, ServerResponse.success(newCategory, "Category added successfully"))
        } catch (e: InvalidInputException) {
            val error = e.message.toString()
            call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(error))
        } catch (t: Exception) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
        }
    }

    delete("/category/{id}") {
        val categoryId = call.parameters["id"]?.trim()?.toLongOrNull()

        try {
            val deletedCategory = categoryService.delete(categoryId)
            call.respond(HttpStatusCode.OK, ServerResponse.success(result = true, deletedCategory))
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

    put("/category") {
        val params = call.receiveParameters()
        val categoryId = params["id"]?.toLongOrNull()
        val categoryName = params["name"]?.trim().orEmpty()

        if (categoryId != null) {
            try {
                if (categoryName.isNotEmpty() && categoryName.length < 4) {
                    call.respond(
                            HttpStatusCode.BadRequest,
                            ServerResponse.error("Category name should be more than 4 character...")
                    )
                } else {
                    categoryService.update(categoryId, categoryName)
                    call.respond(HttpStatusCode.OK, ServerResponse.success("Category updated successfully"))
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        } else {
            call.respond(HttpStatusCode.NotFound, ServerResponse.error("The ID is required..."))
        }
    }
}