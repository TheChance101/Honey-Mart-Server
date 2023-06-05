package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.usecase.category.CategoryUseCasesContainer
import com.thechance.core.data.usecase.category.CreateCategoryUseCase
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidCategoryNameException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
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
            try {
                val products = categoryService.getAllProductsInCategory(categoryId = categoryId)
                call.respond(ServerResponse.success(products))
            } catch (e: InvalidInputException) {

                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }

        post {
            try {
                val params = call.receiveParameters()
                val categoryName = params["name"]?.trim().orEmpty()
                val marketId = params["marketId"]?.toLongOrNull()
                val imageId = params["imageId"]?.toIntOrNull()

                val newCategory = CreateCategoryUseCase(categoryService).invoke(categoryName, marketId, imageId)
                call.respond(HttpStatusCode.Created, ServerResponse.success(newCategory, "Category added successfully"))
            } catch (e: InvalidCategoryNameException) {
                val error = e.message.toString()
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(error))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }


        put {
            try {
                val params = call.receiveParameters()
                val categoryId = params["id"]?.toLongOrNull()
                val categoryName = params["name"]?.trim()
                val marketId = params["marketId"]?.toLongOrNull()
                val imageId = params["imageId"]?.toIntOrNull()

                val isCategoryUpdated = categoryService.update(categoryId, categoryName, marketId, imageId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryUpdated))
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }

        delete("/{id}") {
            val categoryId = call.parameters["id"]?.trim()?.toLongOrNull()

            try {
                val isCategoryDeleted = categoryService.delete(categoryId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryDeleted))
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            } catch (e: ItemNotAvailableException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (t: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(t.message.toString()))
            }
        }
    }
}