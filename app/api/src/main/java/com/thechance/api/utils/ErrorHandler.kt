package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(call: ApplicationCall, block: suspend () -> Unit) {
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

            is InvalidCategoryNameException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("error in category name"))
            }

            is InvalidMarketNameException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("error in market name"))
            }

            is InvalidProductNameException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("error in product name"))
            }

            is InvalidProductQuantityException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("error in quantity."))
            }

            is InvalidProductPriceException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("error in product price"))
            }

            is InvalidCategoryNameLettersException -> {
                call.respond(
                    HttpStatusCode.NotFound,
                    ServerResponse.error("error in InvalidCategoryNameLettersException")
                )
            }

            is InvalidMarketIdException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found market with this id"))
            }

            is InvalidCategoryIdException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found category with this id"))
            }

            is InvalidProductIdException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found product with this id"))
            }

            is InvalidImageIdException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("image not found"))
            }

            is MarketDeletedException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found this market"))
            }

            is CategoryDeletedException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found this category"))
            }

            is ProductDeletedException -> {
                call.respond(HttpStatusCode.NotFound, ServerResponse.error("can not found this product"))
            }

            is NotValidCategoryList -> {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("wrong categories ids"))
            }

            is CategoryNameNotUniqueException -> {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("this category name already exist."))
            }

            is UsernameAlreadyExistException -> {
                call.respond(
                    HttpStatusCode.Conflict,
                    ServerResponse.error("user is already exist", "UsernameAlreadyExistException")
                )
            }

            is InvalidUserIdException -> {
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("invalid user id"))
            }

            is UnKnownUserException -> {
                call.respond(HttpStatusCode.Conflict, ServerResponse.error("Unknown user, please try again"))
            }

            is InvalidUserNameOrPasswordException -> {
                call.respond(
                    HttpStatusCode.Conflict,
                    ServerResponse.error("Invalid username or password, please try again")
                )
            }
            is ProductAlreadyInWishListException -> {
                call.respond(
                    HttpStatusCode.Conflict,
                    ServerResponse.error("This product is  already in wish list")
                )
            }

            is InvalidEmailException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Email is not valid", "InvalidEmailException")
                )
            }

            is InvalidNameException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("Name is not valid", "InvalidNameException")
                )
            }

            is EmailAlreadyExistException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error("This Email already exist", "EmailAlreadyExistException")
                )
            }

            is InvalidPasswordInputException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error(
                        "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters",
                        "InvalidPasswordInputException"
                    )
                )
            }

            is InvalidUserNameInputException -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ServerResponse.error(
                        "The username must be 8 to 14 characters in length and should only include the dot (.) and underscore (_) characters.",
                        "InvalidUserNameInputException"
                    )
                )
            }

            is EmptyCartException -> {
                call.respond(
                    HttpStatusCode.NotFound,
                    ServerResponse.error("The Cart is Empty, please try again with some products")
                )
            }
            is InvalidOrderIdException ->{
                call.respond(HttpStatusCode.BadRequest, ServerResponse.error("Invalid orderId"))
            }

            else -> {
                call.respond(HttpStatusCode.InternalServerError, ServerResponse.error(e.message.toString()))
            }
        }
    }
}