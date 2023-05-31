package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.service.CategoryService
import com.thechance.api.utils.isValidInput
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
                } else if (categoryName.length > 14) {
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
                call.respond(HttpStatusCode.BadRequest, ServerResponse.handleError(e))
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
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.handleError(e))
            }
        }
    }

    put("/category/{id}") {
        val categoryId = call.parameters["id"]?.toLongOrNull()
        val categoryName = call.receiveParameters()["name"]?.trim().orEmpty()

        if (categoryId == null) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Category ID"))
        } else {
            try {
                val isCategoryDeleted = categoryService.isDeleted(categoryId)
                if (isCategoryDeleted) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ServerResponse.error("Category with ID: $categoryId has been deleted")
                    )
                } else if (!isValidInput(categoryName)) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Invalid Category name. Just can contain text and numbers")
                    )
                } else if (categoryName.length < 4 || categoryName.length > 14) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name length should be between 4 and 14 characters")
                    )
                } else {
                    val updatedCategory = categoryService.update(categoryId, categoryName)
                    call.respond(
                        HttpStatusCode.OK,
                        ServerResponse.success(updatedCategory, "Category updated successfully")
                    )
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.handleError(e))
            }
        }
    }

}

