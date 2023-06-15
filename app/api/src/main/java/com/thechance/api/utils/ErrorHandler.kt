package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(cause: Throwable, call: ApplicationCall) {

    val errorResponse = when (cause) {
        is InvalidInputException, is InvalidImageIdException -> {
            ServerResponse.ResponseStatus("missed required fields.", HttpStatusCode.BadRequest.value)
        }

        is ItemNotAvailableException, is CategoryDeletedException, is MarketDeletedException, is ProductDeletedException -> {
            ServerResponse.ResponseStatus("this item not available.", HttpStatusCode.NotFound.value)
        }

        is IdNotFoundException, is InvalidOrderIdException, is InvalidCategoryIdException, is InvalidMarketIdException, is InvalidProductIdException -> {
            ServerResponse.ResponseStatus("This Id not Found", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryNameException, is InvalidMarketNameException, is InvalidProductNameException, is InvalidNameException -> {
            ServerResponse.ResponseStatus("error in name", HttpStatusCode.NotFound.value)
        }

        is InvalidProductQuantityException -> {
            ServerResponse.ResponseStatus("error in quantity.", HttpStatusCode.NotFound.value)
        }

        is InvalidProductPriceException -> {
            ServerResponse.ResponseStatus("error in product price", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryNameLettersException -> {
            ServerResponse.ResponseStatus("error in InvalidCategoryNameLettersException", HttpStatusCode.NotFound.value)
        }

        is NotValidCategoryList -> {
            ServerResponse.ResponseStatus("category ids list not valid", HttpStatusCode.BadRequest.value)
        }

        is CategoryNameNotUniqueException -> {
            ServerResponse.ResponseStatus("Category Name Not Unique Exception", 1002)
        }


        is InvalidUserIdException -> {
            ServerResponse.ResponseStatus("invalid user id", HttpStatusCode.BadRequest.value)
        }

        is UnKnownUserException -> {
            ServerResponse.ResponseStatus("Unknown user, please try again", HttpStatusCode.Conflict.value)
        }

        is InvalidUserNameOrPasswordException -> {
            ServerResponse.ResponseStatus(
                "Invalid username or password, please try again", HttpStatusCode.Conflict.value
            )
        }

        is ProductAlreadyInWishListException -> {
            ServerResponse.ResponseStatus("This product is  already in wish list", HttpStatusCode.Conflict.value)
        }

        is InvalidEmailException -> {
            ServerResponse.ResponseStatus("Email is not valid", HttpStatusCode.BadRequest.value)
        }


        is EmailAlreadyExistException -> {
            ServerResponse.ResponseStatus("This Email already exist", 1001)
        }

        is InvalidPasswordInputException -> {
            ServerResponse.ResponseStatus(
                "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters",
                HttpStatusCode.BadRequest.value
            )
        }

        is EmptyCartException -> {
            ServerResponse.ResponseStatus(
                "The Cart is Empty, please try again with some products",
                HttpStatusCode.NotFound.value
            )
        }


        is ProductNotInSameCartMarketException -> {
            ServerResponse.ResponseStatus(
                "this product not in same market, you should delete cart",
                HttpStatusCode.BadRequest.value
            )
        }

        is InvalidStateOrderException -> {
            ServerResponse.ResponseStatus("State must be 0 or 1", HttpStatusCode.BadRequest.value)
        }

        else -> {
            ServerResponse.ResponseStatus(cause.message.toString(), HttpStatusCode.InternalServerError.value)
        }
    }

    call.respond(HttpStatusCode.NotAcceptable, errorResponse)
}