package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.CategoryService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.lang.Exception

fun Route.categoryRoutes(categoryService: CategoryService) {

    get("/categories") {
        val categories = categoryService.getAllCategories()
        call.respond(ServerResponse.success(categories))
    }

    /**
     * get all products in category
     * */
    get("/category/{categoryId}") {
        val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
        try {
            val products = categoryService.getProductsFromCategory(categoryId = categoryId)
            call.respond(ServerResponse.success(products))
        } catch (t: Throwable) {
            call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(t.message.toString()))
        }
    }

    post("/category") {
        val params = call.receiveParameters()
        val categoryName = params["name"]?.trim().orEmpty()
        val categoryImage = params["image"]?.trim()?.trim().orEmpty()

        try {
            if (categoryName.length < 4 && categoryName.isNotEmpty()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Category name should be more than 4 character...")
                )
            } else if (categoryName.length > 20) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Category name should be less than 20 character...")
                )
            } else if (categoryImage.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("All field is required..."))
            } else {
                categoryService.create(categoryName, categoryImage)
                call.respond(HttpStatusCode.Created, ServerResponse.success("Category added successfully"))
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
        }
    }

    delete("/category/{id}") {
        val params = call.receiveParameters()
        val categoryId = params["id"]?.toLongOrNull()

        if (categoryId != null) {
            try {
                categoryService.remove(categoryId)
                call.respond(HttpStatusCode.OK, ServerResponse.success("Category deleted successfully"))

            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        } else {
            call.respond(HttpStatusCode.NotFound, ServerResponse.error("Invalid Category Id"))
        }
    }

    put("/category/{id}") {
        val params = call.receiveParameters()
        val categoryId = params["id"]?.toLongOrNull()
        val categoryName = params["name"]?.trim().orEmpty()
        val categoryImage = params["image"]?.trim()?.trim().orEmpty()

        if (categoryId != null) {
            try {
                if (categoryName.isNotEmpty() && categoryName.length < 4) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name should be more than 4 character...")
                    )
                } else {
                    categoryService.update(categoryId, categoryName, categoryImage)
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