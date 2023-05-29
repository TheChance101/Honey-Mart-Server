package com.the_chance.endpoints

import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketCategoriesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.marketCategoriesRoutes(marketService: MarketCategoriesService) {

    get("/marketsWithCategories") {
        val markets = marketService.getAllMarketsWithCategories()
        call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
    }

    get("/markets/{id}/categories") {
        val marketId = call.parameters["id"]?.toLongOrNull()
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
                }else{
                    val categories = marketService.getCategoriesByMarket(marketId)
                    call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
                }

            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            }
        }
    }

}
