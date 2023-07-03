package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiMarketDetailsModel
import com.thechance.api.model.mapper.toApiMarketModel
import com.thechance.core.domain.usecase.market.MarketUseCaseContainer
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.marketsRoutes() {

    val marketUseCaseContainer: MarketUseCaseContainer by inject()

    route("/markets") {

        get {
            val page = call.parameters["page"]?.toIntOrNull() ?: 1
            val markets = marketUseCaseContainer.getMarketsUseCase(page).map { it.toApiMarketModel() }
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val categories =
                marketUseCaseContainer.getCategoriesByMarketIdUseCase(marketId).map { it.toApiCategoryModel() }
            call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
        }

        get("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val market = marketUseCaseContainer.getMarketDetailsUseCase(marketId).toApiMarketDetailsModel()
            call.respond(HttpStatusCode.OK, ServerResponse.success(market))
        }


        authenticate {
            put("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val marketName = params["name"]?.trim()
                val description = params["description"]?.trim()

                marketUseCaseContainer.updateMarketUseCase(marketName = marketName, description, marketOwnerId, role)

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }

            put("/{id}/location") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val address = params["address"]?.trim()
                val latitude = params["latitude"]?.trim()?.toDoubleOrNull()
                val longitude = params["longitude"]?.trim()?.toDoubleOrNull()

                marketUseCaseContainer.updateMarketUseCase.updateLocation(
                    marketOwnerId, role, address, latitude, longitude
                )

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }

            put("/{id}/image") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val image = call.receiveMultipart().readAllParts()

                marketUseCaseContainer.updateMarketUseCase.updateImage(marketOwnerId, role, image)

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }
        }

        //TODO: authenticate for Admin only.

        post {
            val params = call.receiveParameters()
            val marketName = params["name"]?.trim().orEmpty()
            val ownerId = params["ownerId"]?.toLongOrNull()

            marketUseCaseContainer.createMarketUseCase(marketName, ownerId)
            call.respond(
                HttpStatusCode.Created,
                ServerResponse.success(true, "Market created successfully")
            )
        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            marketUseCaseContainer.deleteMarketUseCase(marketId)
            call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
        }

    }
}