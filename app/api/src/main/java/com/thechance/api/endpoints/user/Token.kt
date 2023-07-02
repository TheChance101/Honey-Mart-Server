package com.thechance.api.endpoints.user

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiTokens
import com.thechance.core.domain.usecase.RefreshTokenUseCase
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.tokenRouts() {
    val refreshTokenUseCase: RefreshTokenUseCase by inject()
    route("/token") {
        post("/refresh") {
            val params = call.receiveParameters()
            val refreshToken = params["refreshToken"]?.trim()
            val tokens = refreshTokenUseCase(refreshToken).toApiTokens()
            call.respond(ServerResponse.success(tokens))
        }
    }
}