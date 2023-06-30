package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiProductModel
import com.thechance.core.domain.usecase.category.CategoryUseCasesContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.categoryRoutes() {

    val categoryUseCasesContainer: CategoryUseCasesContainer by inject()

    route("/category") {
        authenticate(API_KEY_AUTHENTICATION) {

            get("/{categoryId}/allProduct") {
                val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
                val products = categoryUseCasesContainer.getAllCategoriesUseCase(categoryId = categoryId)
                    .map { it.toApiProductModel() }
                call.respond(ServerResponse.success(products))

            }
        }
        authenticate(JWT_AUTHENTICATION) {

            post {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val categoryName = params["name"]?.trim().orEmpty()
                val imageId = params["imageId"]?.toIntOrNull()

                categoryUseCasesContainer.createCategoryUseCase(categoryName, imageId, marketOwnerId, role)

                call.respond(HttpStatusCode.Created, ServerResponse.success(true, "Category added successfully"))
            }

            put {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val categoryId = params["id"]?.toLongOrNull()
                val categoryName = params["name"]?.trim()
                val imageId = params["imageId"]?.toIntOrNull()

                categoryUseCasesContainer.updateCategoryUseCase(
                    categoryId,
                    categoryName,
                    imageId,
                    marketOwnerId,
                    role
                )
                call.respond(HttpStatusCode.OK, ServerResponse.success("Update successfully"))

            }

            delete("/{categoryId}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val categoryId = call.parameters["categoryId"]?.trim()?.toLongOrNull()
                val isCategoryDeleted =
                    categoryUseCasesContainer.deleteCategoryUseCase(categoryId, marketOwnerId, role)
                call.respond(HttpStatusCode.OK, ServerResponse.success(result = isCategoryDeleted))
            }
        }
    }

}