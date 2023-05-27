package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketService: MarketService) {

    route("/markets") {

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            if (marketName.length < 4) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Market name length should be 4 characters or more")
                )
            } else {
                val newMarket = marketService.createMarket(marketName)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(newMarket, "Market created successfully")
                )
            }
        }


        get {
            val markets = marketService.getAllMarkets()
            call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toIntOrNull()
            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market Id"))
            } else {
                marketService.deleteMarket(marketId)
                call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
            }
        }

        put("/{id}") {
            val marketId = call.parameters["id"]?.toIntOrNull()
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()

            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
            } else if (marketName.length < 4) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Market name length should be 4 characters or more")
                )

            } else {
                try {
                    val updatedMarket = marketService.updateMarket(marketId, marketName)
                    call.respond(
                        HttpStatusCode.OK,
                        ServerResponse.success(updatedMarket, "Market updated successfully")
                    )
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ServerResponse.error(e.message.toString())
                    )
                }

            }
        }
    }

}


