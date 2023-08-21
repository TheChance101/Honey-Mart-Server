package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiCategoryModel
import com.thechance.api.model.mapper.toApiMarketDetailsModel
import com.thechance.api.model.mapper.toApiMarketModel
import com.thechance.api.model.market.MarketIdModel
import com.thechance.core.domain.usecase.market.MarketUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.http.content.*
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

        authenticate(API_KEY_AUTHENTICATION) {
            get {
                val page = call.parameters["page"]?.toIntOrNull() ?: 1
                val markets = marketUseCaseContainer.getMarketsUseCase(page).map { it.toApiMarketModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
            }

            get("/{id}/categories") {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val categories =
                    marketUseCaseContainer.getCategoriesByMarketIdUseCase(marketId).map { it.toApiCategoryModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(categories))
            }

            get("/{id}") {
                val marketId = call.parameters["id"]?.toLongOrNull()
                val market = marketUseCaseContainer.getMarketDetailsUseCase(marketId).toApiMarketDetailsModel()
                call.respond(HttpStatusCode.OK, ServerResponse.success(market))
            }
        }


        authenticate(JWT_AUTHENTICATION) {


            get("/marketValidation") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val isApproved = marketUseCaseContainer.checkMarketApprovedUseCase(marketOwnerId,role)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(isApproved)
                )
            }

            put("/updateMarket") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val marketName = params["name"]?.trim()
                val description = params["description"]?.trim()

                marketUseCaseContainer.updateMarketUseCase(marketName = marketName, description, marketOwnerId, role)

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }

            /**
             * 1 => open
             * 2 =>close
             * */
            put("/status") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val status = params["status"]?.trim()?.toIntOrNull()
                marketUseCaseContainer.updateMarketStatus(marketOwnerId, role, status)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }

            put("/location") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val params = call.receiveParameters()
                val address = params["address"]?.trim()
                val latitude = params["latitude"]?.trim()?.toDoubleOrNull()
                val longitude = params["longitude"]?.trim()?.toDoubleOrNull()

                marketUseCaseContainer.updateMarketUseCase.updateLocation(
                    marketOwnerId, role, address, latitude, longitude
                )

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }

            put("/image") {
                val principal = call.principal<JWTPrincipal>()
                val marketOwnerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)

                val image = call.receiveMultipart().readAllParts()

                marketUseCaseContainer.updateMarketUseCase.updateImage(marketOwnerId, role, image)

                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "Market updated successfully")
                )
            }


            post {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val params = call.receiveParameters()
                val name = params["name"]?.trim()
                val address = params["address"]?.trim()
                val description = params["description"]?.trim()

                val marketId = marketUseCaseContainer.createMarketUseCase(ownerId, role, name, address, description)
                call.respond(
                    HttpStatusCode.Created,
                    ServerResponse.success(MarketIdModel(marketId))
                )
            }

            delete("/{id}") {
                val marketId = call.parameters["id"]?.toLongOrNull()
                marketUseCaseContainer.deleteMarketUseCase(marketId)
                call.respond(HttpStatusCode.OK, ServerResponse.success("Market Deleted Successfully"))
            }

        }
    }
}