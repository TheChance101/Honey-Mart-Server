package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.data.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(
    call: ApplicationCall,
    block: suspend () -> Unit,
) {
    try {
        block()
    } catch (e: Exception) {
        when (e) {
            is InvalidInputException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("All fields required.")
            )

            is ItemNotAvailableException -> call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("this item not available.")
            )

            is IdNotFoundException -> call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("This Id not Found")
            )

            else -> {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ServerResponse.error(e.message.toString())
                )
            }
        }
    }
}