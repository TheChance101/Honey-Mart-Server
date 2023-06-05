package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun checkResponse(
    call: ApplicationCall,
    function: suspend () -> Unit,
) {
    try {
        function()
    } catch (e: InvalidInputException) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
    } catch (e: ItemNotAvailableException) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
    } catch (e: IdNotFoundException) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
    }
}