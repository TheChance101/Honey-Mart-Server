package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.utils.handleException
import com.thechance.core.data.usecase.category.CategoryUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(categoryUseCasesContainer: CategoryUseCasesContainer) {

    route("/category") {
        /**
         * get all products in category
         * */
        get("/{categoryId}") {
            val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
            handleException(call) {
                val products = categoryUseCasesContainer.getAllCategoriesUseCase(categoryId = categoryId)
                call.respond(ServerResponse.success(products))
            }
        }

        post {
            handleException(call) {
                val params = call.receiveParameters()
                val categoryName = params["name"]?.trim().orEmpty()
                val marketId = params["marketId"]?.toLongOrNull()
                val imageId = params["imageId"]?.toIntOrNull()
                val newCategory =
                    categoryUseCasesContainer.createCategoryUseCase(categoryName, marketId, imageId)
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

                val isCategoryUpdated =
                    categoryUseCasesContainer.updateCategoryUseCase(categoryId, categoryName, marketId, imageId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryUpdated))

            }
        }

        delete("/{id}") {
            val categoryId = call.parameters["id"]?.trim()?.toLongOrNull()
            handleException(call) {
                val isCategoryDeleted = categoryUseCasesContainer.deleteCategoryUseCase.invoke(categoryId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryDeleted))

            }
        }
    }
}