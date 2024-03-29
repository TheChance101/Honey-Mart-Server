package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.domain.usecase.DeleteAllTablesUseCase
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.deleteAllTables() {
    val deleteAllTablesUseCase: DeleteAllTablesUseCase by inject()
    authenticate(API_KEY_AUTHENTICATION) {
        delete("/deleteAllTables") {
            try {
                deleteAllTablesUseCase()
                call.respond(HttpStatusCode.OK, ServerResponse.success("All Tables Deleted Successfully"))
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ServerResponse.error("Error!!", HttpStatusCode.InternalServerError.value)
                )
            }
        }
    }
}