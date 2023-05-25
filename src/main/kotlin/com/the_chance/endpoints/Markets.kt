package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketsRoutes(marketService: MarketService) {

    post("/markets") {
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
                ServerResponse.success(newMarket, "Market created Successfully")
            )
        }
    }

    get("/markets") {
        val markets = marketService.getAllMarkets()
        call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
    }
}