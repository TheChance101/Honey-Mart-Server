package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.usecase.market.MarketUseCaseContainer
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketUseCaseContainer: MarketUseCaseContainer) {

    route("/markets") {

        get {
            val markets = marketUseCaseContainer.getMarketsUseCase()
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            try {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val categories = marketUseCaseContainer.getCategoriesByMarketIdUseCase(marketId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            }
        }

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            try {
                val newMarket = marketUseCaseContainer.createMarketUseCase(marketName)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newMarket, "Market created successfully")
                )
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            }
        }

        put("/{id}") {
            try {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val marketName = call.receiveParameters()["name"]?.trim()
                val updatedMarket = marketUseCaseContainer.updateMarketUseCase(marketId, marketName)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(updatedMarket, "Market updated successfully")
                )
            } catch (e: ItemNotAvailableException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: InvalidInputException) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
            } else {
                try {
                    marketUseCaseContainer.deleteMarketUseCase(marketId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
                } catch (e: ItemNotAvailableException) {
                    call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
                } catch (e: IdNotFoundException) {
                    call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
                }
            }
        }
    }
}


