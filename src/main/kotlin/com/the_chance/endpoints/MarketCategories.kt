package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketCategoriesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketCategoriesRoutes(marketService: MarketCategoriesService) {

    get("/allMarkets") {
        val markets = marketService.getAllMarketsWithCategories()
        call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
    }

}
