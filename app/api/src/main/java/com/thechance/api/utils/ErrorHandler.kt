package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(cause: Throwable, call: ApplicationCall) {
    when (cause) {
        is InvalidInputException -> call.respond(
            HttpStatusCode.BadRequest,
            ServerResponse.error("All fields required.", HttpStatusCode.BadRequest.value)
        )

        is ItemNotAvailableException -> call.respond(
            HttpStatusCode.NotFound,
            ServerResponse.error("this item not available.", HttpStatusCode.NotFound.value)
        )

        is IdNotFoundException -> call.respond(
            HttpStatusCode.NotFound,
            ServerResponse.error("This Id not Found", HttpStatusCode.NotFound.value)
        )

        is InvalidCategoryNameException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in category name", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidMarketNameException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in market name", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidProductNameException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in product name", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidProductQuantityException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in quantity.", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidProductPriceException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in product price", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidCategoryNameLettersException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("error in InvalidCategoryNameLettersException", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidMarketIdException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found market with this id", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidCategoryIdException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found category with this id", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidProductIdException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found product with this id", HttpStatusCode.NotFound.value)
            )
        }

        is InvalidImageIdException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("image not found", HttpStatusCode.NotFound.value)
            )
        }

        is MarketDeletedException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found this market", HttpStatusCode.NotFound.value)
            )
        }

        is CategoryDeletedException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found this category", HttpStatusCode.NotFound.value)
            )
        }

        is ProductDeletedException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error("can not found this product", HttpStatusCode.NotFound.value)
            )
        }

        is NotValidCategoryList -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("categories not valid", HttpStatusCode.BadRequest.value)
            )
        }

        is CategoryNameNotUniqueException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("this category name ", HttpStatusCode.BadRequest.value)
            )
        }

        is UsernameAlreadyExistException -> {
            call.respond(
                HttpStatusCode.Conflict,
                ServerResponse.error("user is already exist", HttpStatusCode.Conflict.value)
            )
        }

        is InvalidUserIdException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("invalid user id", HttpStatusCode.BadRequest.value)
            )
        }

        is UnKnownUserException -> {
            call.respond(
                HttpStatusCode.Conflict,
                ServerResponse.error("Unknown user, please try again", HttpStatusCode.Conflict.value)
            )
        }

        is InvalidUserNameOrPasswordException -> {
            call.respond(
                HttpStatusCode.Conflict,
                ServerResponse.error("Invalid username or password, please try again", HttpStatusCode.Conflict.value)
            )
        }

        is ProductAlreadyInWishListException -> {
            call.respond(
                HttpStatusCode.Conflict,
                ServerResponse.error("This product is  already in wish list", HttpStatusCode.Conflict.value)
            )
        }

        is InvalidEmailException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("Email is not valid", HttpStatusCode.BadRequest.value)
            )
        }

        is InvalidNameException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("Name is not valid", HttpStatusCode.BadRequest.value)
            )
        }

        is EmailAlreadyExistException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("This Email already exist", 1001)
            )
        }

        is InvalidPasswordInputException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error(
                    "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters",
                    HttpStatusCode.BadRequest.value
                )
            )
        }

        is InvalidUserNameInputException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error(
                    "The username must be 8 to 14 characters in length and should only include the dot (.) and underscore (_) characters.",
                    HttpStatusCode.BadRequest.value
                )
            )
        }

        is EmptyCartException -> {
            call.respond(
                HttpStatusCode.NotFound,
                ServerResponse.error(
                    "The Cart is Empty, please try again with some products",
                    HttpStatusCode.NotFound.value
                )
            )
        }

        is InvalidOrderIdException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error("Invalid orderId", HttpStatusCode.BadRequest.value)
            )
        }

        is ProductNotInSameCartMarketException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error(
                    "this product not in same market, you should delete cart",
                    HttpStatusCode.BadRequest.value
                )
            )
        }

        is InvalidStateOrderException -> {
            call.respond(
                HttpStatusCode.BadRequest,
                ServerResponse.error(
                    "State must be 0 or 1",
                    HttpStatusCode.BadRequest.value
                )
            )
        }

        else -> {
            call.respond(
                HttpStatusCode.InternalServerError,
                ServerResponse.error(cause.message.toString(), HttpStatusCode.InternalServerError.value)
            )
        }
    }
}