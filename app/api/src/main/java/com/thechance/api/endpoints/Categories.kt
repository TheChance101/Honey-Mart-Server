package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(categoryService: CategoryService) {

    route("/category") {
        /**
         * get all products in category
         * */
        get("/{categoryId}") {
            val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val products = categoryService.getAllProductsInCategory(categoryId = categoryId)
                call.respond(ServerResponse.success(products))
            }
        }

        post {
            val params = call.receiveParameters()
            val categoryName = params["name"]?.trim().orEmpty()
            val marketId = params["marketId"]?.toLongOrNull()
            val imageId = params["imageId"]?.toIntOrNull()

            handleException(call) {
                val newCategory = categoryService.create(categoryName, marketId, imageId)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newCategory, "Category added successfully"))
            }
        }


        put {
            handleException(call) {
                val params = call.receiveParameters()
                val categoryId = params["id"]?.toLongOrNull()
                val categoryName = params["name"]?.trim()
                val marketId = params["marketId"]?.toLongOrNull()
                val imageId = params["imageId"]?.toIntOrNull()

                val isCategoryUpdated = categoryService.update(categoryId, categoryName, marketId, imageId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryUpdated))
            }
        }

        delete("/{id}") {
            val categoryId = call.parameters["id"]?.trim()?.toLongOrNull()

            handleException(call) {
                val isCategoryDeleted = categoryService.delete(categoryId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryDeleted))
            }
        }
    }
}