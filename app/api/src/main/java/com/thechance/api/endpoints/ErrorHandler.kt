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
                ServerResponse.error("this item not available.")
            )

            is IdNotFoundException -> call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("This Id  not Found")
            )

            is InternalServiceErrorException -> call.respond(
                HttpStatusCode.InternalServerError,
                ServerResponse.error("Error!!")
            )

            is MarketNameLengthException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The name should be between 4 to 14 letter")
            )

            is MarketNameWithSymbolException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The name should be only letters and numbers")
            )

            is MarketNameNotFound -> call.respond(HttpStatusCode.NotFound, ServerResponse.error(""))
            is ItemAlreadyDeleted -> call.respond(HttpStatusCode.BadRequest, ServerResponse.error(""))
            is MarketItemDeletedException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("This Market is already deleted")
            )

            is ProductUpdateException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("Can't do UPDATE without fields to update")
            )

            is ProductNameLengthException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product name should have a length greater than 6 and shorter than 20 characters.")
            )

            is ProductEmptyNameException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product name can not be empty")
            )

            is ProductEmptyQuantityException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product quantity can not be empty")
            )

            is ProductQuantityLengthException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product Quantity should have value and shorter than 20 characters.")
            )
            is ProductPriceRangeException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product Price should be in range 0.1 to 1000.000.")
            )

            is ProductEmptyPriceException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product price can not be empty")
            )

            is ProductAssignCategoryException -> call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("The product must be assigned to at least one category.")
            )
            is CategoryNotFoundException -> call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("This categoryId not found")
            )
        }
    }
}