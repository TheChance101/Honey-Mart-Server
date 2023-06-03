package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.service.DeleteAllTablesService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteAllTables(deleteAllService: DeleteAllTablesService) {

    delete("/deleteAllTables") {
        try {
            deleteAllService.deleteAllTables()
            call.respond(HttpStatusCode.OK, ServerResponse.success("All Tables Deleted Successfully"))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, ServerResponse.error("Error!!"))
        }

    }
}