package com.thechance.api.utils

import com.thechance.api.ServerResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun Error.errorHandler(call: ApplicationCall) {
    when (this.error) {
        ErrorType.INVALID_INPUT -> {
            call.respond(HttpStatusCode.NotAcceptable, ServerResponse.error(error.message))
        }

        ErrorType.NOT_FOUND, ErrorType.DELETED_ITEM -> {
            call.respond(HttpStatusCode.NotFound, ServerResponse.error(error.message))
        }
    }
}

class InvalidInputException(message: String) : Exception(message)
class IdNotFoundException(message: String) : Exception(message)
class ItemNotAvailableException(message: String) : Exception(message)