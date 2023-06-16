package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiMarketModel
import com.thechance.core.domain.usecase.market.MarketUseCaseContainer
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.marketsRoutes() {

    val marketUseCaseContainer: MarketUseCaseContainer by inject()

    route("/markets") {

//        install(CachingHeaders) {
//            options { call, content -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 1800)) }
//        }

        get {
            val markets = marketUseCaseContainer.getMarketsUseCase().map { it.toApiMarketModel() }
//            call.caching = CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 900))
            call.respond( ServerResponse.success(markets))
        }

        get("/{id}/categories") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val categories =
                marketUseCaseContainer.getCategoriesByMarketIdUseCase(marketId).map { it.toApiCategoryModel() }
            call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
        }



        post {
            val marketName = call.receiveParameters()["name"]?.trim().orEmpty()
            val newMarket = marketUseCaseContainer.createMarketUseCase(marketName).toApiMarketModel()
            call.respond(
                HttpStatusCode.Created,
                ServerResponse.success(newMarket, "Market created successfully")
            )

        }


        put("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            val marketName = call.receiveParameters()["name"]?.trim()
            val updatedMarket = marketUseCaseContainer.updateMarketUseCase(marketId, marketName).toApiMarketModel()

            call.respond(
                HttpStatusCode.OK,
                ServerResponse.success(updatedMarket, "Market updated successfully")
            )

        }

        delete("/{id}") {
            val marketId = call.parameters["id"]?.toLongOrNull()
            marketUseCaseContainer.deleteMarketUseCase(marketId)
            call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))

        }
    }
}