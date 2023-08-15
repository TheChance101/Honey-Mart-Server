package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiMarketModel
import com.thechance.api.model.mapper.toApiTokens
import com.thechance.core.domain.usecase.admin.AdminUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.adminRoutes() {

    val adminUseCase: AdminUseCaseContainer by inject()

    route("/admin") {
        authenticate(API_KEY_AUTHENTICATION) {
            post("/login") {
                val params = call.receiveParameters()
                val email = params["email"]?.trim().toString()
                val password = params["password"]?.trim().toString()
                val token = adminUseCase.verifyAdminUseCase(email, password).toApiTokens()
                call.respond(HttpStatusCode.Created, ServerResponse.success(token, "Logged in Successfully"))
            }

            get("/markets") {
                val markets = adminUseCase.getUnApprovedMarkets().map { it.toApiMarketModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
            }

            put("/approve/{id}") {
                val params = call.receiveParameters()
                val marketId = params["marketId"]?.trim()?.toLong()
                val isApproved = params["isApproved"]?.trim().toBoolean()
                adminUseCase.approveMarketUseCase(marketId!!, isApproved)
                call.respond(HttpStatusCode.OK, ServerResponse.success(true, "Market updated successfully"))
            }
        }
    }
}