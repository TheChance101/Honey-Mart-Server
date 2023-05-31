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

    route("category") {

        post("/{marketId}") {
            val categoryName = call.receiveParameters()["categoryName"]?.trim().orEmpty()
            val marketId = call.parameters["marketId"]?.toLongOrNull()

            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
                return@post
            }

            try {
                val isMarketDeleted = categoryService.isMarketDeleted(marketId)
                if (isMarketDeleted) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Market with ID: $marketId has been deleted.")
                    )
                    return@post
                }

                val categoryList = categoryService.getCategoriesByMarketId(marketId)
                val isNameRepeated = categoryList.any { it.categoryName == categoryName }
                if (isNameRepeated) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("This category with name $categoryName already exists.")
                    )
                    return@post
                }

                if (categoryName.length < 4 && categoryName.isNotEmpty()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name should be more than 4 characters.")
                    )
                    return@post
                }
                if (categoryName.length > 20) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error("Category name should be less than 20 characters.")
                    )
                    return@post
                }

                val newCategory = categoryService.create(categoryName, marketId)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newCategory, "Category added successfully")
                )
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        }


        put("/{categoryId}/{marketId}") {

            val categoryId = call.parameters["categoryId"]?.toLongOrNull()
            val marketId = call.parameters["marketId"]?.toLongOrNull()
            val categoryName = call.receiveParameters()["categoryName"]?.trim().orEmpty()

            if (categoryId == null || marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Category or market ID"))
            } else {
                try {
                    val isMarketDeleted = categoryService.isMarketDeleted(marketId)
                    val isCategoryDeleted = categoryService.isCategoryDeleted(categoryId)
                    if (isMarketDeleted) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            ServerResponse.error("Market with ID: $marketId has been deleted")
                        )
                    } else if (isCategoryDeleted) {
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
                    call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
                }
            }
        }

        delete("/{categoryId}/{marketId}") {
            val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
            val marketId = call.parameters["marketId"]?.trim()?.toLongOrNull()

            if (categoryId == null || marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Category or market ID"))
            } else {
                try {
                    val isDeleted = categoryService.delete(categoryId, marketId)
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
                    call.respond(HttpStatusCode.BadRequest,e.message.toString())
                }
            }
        }

    }

}

