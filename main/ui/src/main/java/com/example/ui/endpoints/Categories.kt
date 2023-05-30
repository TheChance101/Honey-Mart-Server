package com.example.ui.endpoints

import com.example.ui.ServerResponse
import com.example.ui.service.CategoryService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(categoryService: CategoryService) {

    post("/category") {
        val params = call.receiveParameters()
        val categoryName = params["name"]?.trim().orEmpty()
        val marketId = params["marketId"]?.toLongOrNull()

        if (marketId == null) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
        } else {
            try {
                val isMarketDeleted = categoryService.isDeleted(marketId)
                if (isMarketDeleted) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ServerResponse.error("Market with ID: $marketId has been deleted")
                    )
                } else if (categoryName.length < 4 && categoryName.isNotEmpty()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name should be more than 4 character...")
                    )
                } else if (categoryName.length > 20) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name should be less than 20 character...")
                    )
                } else {
                    val newCategory = categoryService.create(categoryName, marketId)
                    call.respond(
                        HttpStatusCode.Created,
                        ServerResponse.success(newCategory, "Category added successfully")
                    )
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            }
        }
    }

    delete("/category/{id}") {
        val categoryId = call.parameters["id"]?.trim()?.toLongOrNull()

        if (categoryId == null) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Category ID"))
        } else {
            try {
                val isDeleted = categoryService.delete(categoryId)
                if (isDeleted) {
                    call.respond(
                        HttpStatusCode.OK,
                        ServerResponse.success("Category Deleted Successfully")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category with ID $categoryId already deleted")
                    )
                }
            } catch (e: NoSuchElementException) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ServerResponse.error(e.message.toString())
                )
            }
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