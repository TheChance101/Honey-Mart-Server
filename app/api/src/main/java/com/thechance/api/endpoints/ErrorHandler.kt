package com.thechance.api.endpoints

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
                ServerResponse.error(e.message.toString())
            )

            is IdNotFoundException -> call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("This Id  not Found")
            )

            is InternalServiceErrorException -> call.respond(
                HttpStatusCode.InternalServerError,
                ServerResponse.error(e.message.toString())
            )

            is MarketNameLengthException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The name should be between 4 to 14 letter")
            )

            is MarketNameWithSymbolException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The name should be only letters and numbers")
            )

            is MarketNameNotFound -> call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
            is ItemAlreadyDeleted -> call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
            is MarketItemDeletedException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("This Market is already deleted")
            )
        }
    }
}