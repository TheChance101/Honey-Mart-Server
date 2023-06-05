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
    } catch (e: InvalidInputException) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
    } catch (e: ItemNotAvailableException) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
    } catch (e: IdNotFoundException) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
    } catch (e: InternalServiceErrorException) {
        call.respond(HttpStatusCode.InternalServerError, ServerResponse.error(e.message.toString()))
    } catch (e: MarketNameNotFound) {
        call.respond(HttpStatusCode.NotFound, ServerResponse.error(e.message.toString()))
    }catch (e: ItemAlreadyDeleted) {
        call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
    }catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, ServerResponse.error(e.message.toString()))
    }
}