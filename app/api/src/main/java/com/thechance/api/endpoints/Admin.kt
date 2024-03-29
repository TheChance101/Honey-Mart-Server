package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiAdmin
import com.thechance.api.model.mapper.toApiMarketRequestModel
import com.thechance.core.domain.usecase.admin.AdminUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
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
                val admin = adminUseCase.verifyAdminUseCase(email, password).toApiAdmin()
                call.respond(HttpStatusCode.Created, ServerResponse.success(admin, "Logged in Successfully"))
            }

        }
        authenticate(JWT_AUTHENTICATION) {
            get("/markets") {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val isApprovedParam = call.request.queryParameters["isApproved"]
                val isApproved = isApprovedParam?.trim()?.toBooleanStrictOrNull()
                val markets = adminUseCase.getMarketsRequestsDetails(role, isApproved).map { it.toApiMarketRequestModel() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(markets))
            }
            get{
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                adminUseCase.authenticateAdminUseCase(role)
                call.respond(HttpStatusCode.OK, ServerResponse.success("Authentication success"))
            }

            put("/request/{id}") {
                val principal = call.principal<JWTPrincipal>()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val params = call.receiveParameters()
                val marketId = call.parameters["id"]?.trim()?.toLongOrNull()
                val isApproved = params["isApproved"]?.trim().toBoolean()
                adminUseCase.approveMarketUseCase(marketId, isApproved, role)
                call.respond(HttpStatusCode.OK, ServerResponse.success(true, "Market updated successfully"))
            }
        }
    }

}