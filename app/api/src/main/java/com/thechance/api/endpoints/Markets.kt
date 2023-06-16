package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiMarketModel
import com.thechance.core.domain.usecase.market.MarketUseCaseContainer
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
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
            val markets = marketUseCaseContainer.getMarketsUseCase().map { it.toApiMarketModel() }
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val categories =
                marketUseCaseContainer.getCategoriesByMarketIdUseCase(marketId).map { it.toApiCategoryModel() }
            call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
        }


        authenticate {
            put("/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val marketName = call.receiveParameters()["name"]?.trim()
                val updatedMarket =
                    marketUseCaseContainer.updateMarketUseCase(marketName, marketOwnerId, role)
                        .toApiMarketModel()
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(updatedMarket, "Market updated successfully")
                )
            }
        }

        //TODO: authenticate for Admin only.

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            val newMarket = marketUseCaseContainer.createMarketUseCase(marketName).toApiMarketModel()
            call.respond(
                HttpStatusCode.Created,
                ServerResponse.success(newMarket, "Market created successfully")
            )
        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            marketUseCaseContainer.deleteMarketUseCase(marketId)
            call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
        }

    }
}