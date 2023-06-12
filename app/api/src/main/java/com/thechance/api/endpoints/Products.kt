package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.mapper.toApiCategoryModel
import com.thechance.api.mapper.toApiProductModel
import com.thechance.api.utils.orZero
import com.thechance.api.utils.toLongIds
import com.thechance.core.domain.usecase.product.ProductUseCasesContainer
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.productsRoutes() {

    val productUseCasesContainer: ProductUseCasesContainer by inject()

    route("/product") {

        get("/{productId}") {
            val productId = call.parameters["productId"]?.trim()?.toLongOrNull()

            val categories = productUseCasesContainer.getCategoriesForProductUseCase(productId = productId)
                .map { it.toApiCategoryModel() }
            call.respond(ServerResponse.success(categories))

        }

        post {

            val params = call.receiveParameters()
            val productName = params["name"]?.trim().orEmpty()
            val productPrice = params["price"]?.trim()?.toDoubleOrNull().orZero()
            val productQuantity = params["quantity"]?.trim()
            val categoriesId = params["categoriesId"]?.trim()?.toLongIds()

            val newAddedProduct = productUseCasesContainer.createProductUseCase(
                productName, productPrice, productQuantity, categoriesId
            ).toApiProductModel()
            call.respond(HttpStatusCode.Created, ServerResponse.success(newAddedProduct))

        }

        put("{id}") {

            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            val params = call.receiveParameters()
            val productName = params["name"]?.trim()
            val productPrice = params["price"]?.trim()?.toDoubleOrNull()
            val productQuantity = params["quantity"]?.trim()

            productUseCasesContainer.updateProductUseCase(
                productId = productId,
                productName = productName,
                productPrice = productPrice,
                productQuantity = productQuantity
            )
            call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))

        }

        put("{id}/updateCategories") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()
            val params = call.receiveParameters()
            val categoriesId = params["categoriesId"]?.trim().toLongIds()


            productUseCasesContainer.updateProductCategoryUseCase(productId = productId, categoryIds = categoriesId)
            call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))

        }

        delete("{id}") {
            val productId = call.parameters["id"]?.trim()?.toLongOrNull()

            val result = productUseCasesContainer.deleteProductUseCase(productId = productId)
            call.respond(
                HttpStatusCode.OK,
                ServerResponse.success(result = result, successMessage = "Product Deleted successfully.")
            )

        }
    }
}

