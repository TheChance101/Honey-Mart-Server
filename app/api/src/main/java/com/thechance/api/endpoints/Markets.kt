package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.service.MarketService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketService: MarketService) {

    route("/markets") {

        get {
            val markets = marketService.getAllMarkets()
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            handleException(call) {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val categories = marketService.getCategoriesByMarket(marketId)
                call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
            }
        }

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            handleException(call) {
                val newMarket = marketService.createMarket(marketName)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newMarket, "Market created successfully")
                )
            }
        }

        put("/{id}") {
            handleException(call) {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val marketName = call.receiveParameters()["name"]?.trim()
                val updatedMarket = marketService.updateMarket(marketId, marketName)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(updatedMarket, "Market updated successfully")
                )
            }
        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
            } else {
                handleException(call) {
                    marketService.deleteMarket(marketId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success(true, "Market Deleted Successfully"))
                }
            }
        }
    }
}


