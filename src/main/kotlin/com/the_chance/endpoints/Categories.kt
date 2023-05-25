package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.CategoryService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(categoryService: CategoryService) {

    get("/categories") {
        val categories = categoryService.getAllCategories()
        call.respond(ServerResponse.success(categories))
    }

    post("/category") {
        val params = call.receiveParameters()
        val categoryName = params["name"]?.trim().orEmpty()
        val categoryImage = params["image"]?.trim()?.trim().orEmpty()

        val newAddedProduct = categoryService.create(categoryName, categoryImage)
        call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))
    }

    delete("/category/{id}") {
        val params = call.receiveParameters()
        val categoryId = params["id"]?.toInt()
        val isDeleted = categoryId?.let { id -> categoryService.remove(id) }

        if (isDeleted == true){
            call.respond(HttpStatusCode.OK, ServerResponse.success(isDeleted))
        }else{
            call.respond(HttpStatusCode.NotFound, "User not found")
        }

    }

    put("/category/{id}") {
        val params = call.receiveParameters()
        val categoryId = params["id"]?.toInt() ?: -1
        val categoryName = params["name"]?.trim().orEmpty()
        val categoryImage = params["image"]?.trim()?.trim().orEmpty()

        val isUpdated = categoryService.update(categoryId, categoryName, categoryImage)

        if (isUpdated) {
            call.respond(HttpStatusCode.OK, ServerResponse.success(isUpdated))
        } else {
            call.respond(HttpStatusCode.NotFound, "Category not found")
        }
    }
}