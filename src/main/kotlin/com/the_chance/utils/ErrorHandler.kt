package com.the_chance.utils

import com.example.core.data.validation.Error
import com.example.core.data.validation.ErrorType
import com.example.ui.ServerResponse
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