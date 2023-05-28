package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketService
import com.the_chance.utils.isValidStringInput
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketService: MarketService) {

    route("/markets") {

        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            if (!isValidStringInput(marketName)) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Invalid market name. It should only contain letters without numbers or symbols.")
                )
            } else if (marketName.length < 4 || marketName.length > 14) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Market name length should be between 4 and 14 characters")
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
            val marketId = call.parameters["id"]?.toLongOrNull()
            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
            } else {
                try {
                    val isDeleted = marketService.deleteMarket(marketId)
                    if (isDeleted) {
                        call.respond(
                            HttpStatusCode.OK,
                            ServerResponse.success("Market Deleted Successfully")
                        )
                    } else {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            ServerResponse.error("Market with ID $marketId already deleted")
                        )
                    }
                } catch (e: NoSuchElementException) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ServerResponse.error(e.message.toString())
                    )
                }
            }
        }

        put("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()

            if (marketId == null) {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid Market ID"))
            } else {
                try {
                    val isMarketDeleted = marketService.isDeleted(marketId)
                    if (isMarketDeleted) {
                        call.respond(
                            HttpStatusCode.NotFound,
                            ServerResponse.error("Market with ID: $marketId has been deleted")
                        )
                    } else if (!isValidStringInput(marketName)) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            ServerResponse.error("Invalid market name. It should only contain letters without numbers or symbols.")
                        )
                    } else if (marketName.length < 4 || marketName.length > 14) {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            ServerResponse.error("Market name length should be between 4 and 14 characters")
                        )
                    } else {
                        val updatedMarket = marketService.updateMarket(marketId, marketName)
                        call.respond(
                            HttpStatusCode.OK,
                            ServerResponse.success(updatedMarket, "Market updated successfully")
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ServerResponse.error(e.message.toString())
                    )
                }
            }
        }

    }
}


