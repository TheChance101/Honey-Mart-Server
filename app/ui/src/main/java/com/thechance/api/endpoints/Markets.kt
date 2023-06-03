package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.service.MarketService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketService: MarketService) {

    get("/marketsWithCategories") {
        val markets = marketService.getAllMarketsWithCategories()
        call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
    }

    route("/markets") {

        get {
            val markets = marketService.getAllMarkets()
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            try {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val categories = marketService.getCategoriesByMarket(marketId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
            } catch (e: IdNotFoundException) {
                call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        }

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            try {
                val newMarket = marketService.createMarket(marketName)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newMarket, "Market created successfully")
                )
            } catch (e: InvalidInputException) {
                call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
            }
        }

        put("/{id}") {
            try {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val marketName = call.receiveParameters()["name"]?.trim()
                val updatedMarket = marketService.updateMarket(marketId, marketName)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(updatedMarket, "Market updated successfully")
                )
            } catch (e: ItemNotAvailableException) {
                call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
            } catch (e: InvalidInputException) {
                call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
            } catch (e: IdNotFoundException) {
                call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
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
                    marketService.deleteMarket(marketId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
                } catch (e: ItemNotAvailableException) {
                    call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
                } catch (e: IdNotFoundException) {
                    call.respond(e.statusCode, ServerResponse.error(e.message.toString()))
                }
            }
        }
    }
}


