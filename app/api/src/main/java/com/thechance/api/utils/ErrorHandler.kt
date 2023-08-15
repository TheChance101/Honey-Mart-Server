package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(cause: Throwable, call: ApplicationCall) {
    val messageResponse = when (cause) {
        is InvalidInputException -> {
            ServerResponse.error("All fields required.", HttpStatusCode.BadRequest.value)
        }

        is ItemNotAvailableException -> {
            ServerResponse.error("this item not available.", HttpStatusCode.NotFound.value)
        }

        is IdNotFoundException -> {
            ServerResponse.error("This Id not Found", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryNameException -> {
            ServerResponse.error("error in category name", HttpStatusCode.NotFound.value)
        }

        is InvalidMarketNameException -> {
            ServerResponse.error("error in market name", HttpStatusCode.NotFound.value)
        }

        is InvalidProductNameException -> {
            ServerResponse.error("error in product name", HttpStatusCode.NotFound.value)
        }

        is InvalidProductDescriptionException -> {
            ServerResponse.error("error in quantity.", HttpStatusCode.NotFound.value)
        }
        is InvalidDescriptionException -> {
            ServerResponse.error("Invalid description.", HttpStatusCode.NotFound.value)
        }

        is InvalidProductPriceException -> {
            ServerResponse.error("error in product price", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryNameLettersException -> {
            ServerResponse.error("error in InvalidCategoryNameLettersException", HttpStatusCode.NotFound.value)
        }

        is InvalidMarketIdException -> {
            ServerResponse.error("can not found market with this id", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryIdException -> {
            ServerResponse.error("can not found category with this id", HttpStatusCode.NotFound.value)
        }

        is InvalidProductIdException -> {
            ServerResponse.error("can not found product with this id", HttpStatusCode.NotFound.value)
        }

        is InvalidImageIdException -> {
            ServerResponse.error("image not found", HttpStatusCode.NotFound.value)
        }

        is MarketDeletedException -> {
            ServerResponse.error("can not found this market", HttpStatusCode.NotFound.value)
        }

        is CategoryDeletedException -> {
            ServerResponse.error("can not found this category", HttpStatusCode.NotFound.value)
        }

        is ProductDeletedException -> {
            ServerResponse.error("can not found this product", HttpStatusCode.NotFound.value)
        }

        is NotValidCategoryList -> {
            ServerResponse.error("categories not valid", HttpStatusCode.BadRequest.value)
        }

        is CategoryNameNotUniqueException -> {
            ServerResponse.error("this category name already in the market", HttpStatusCode.BadRequest.value)
        }

        is UsernameAlreadyExistException -> {
            ServerResponse.error("user is already exist", HttpStatusCode.Conflict.value)
        }

        is InvalidUserIdException -> {
            ServerResponse.error("invalid user id", HttpStatusCode.BadRequest.value)
        }

        is UnKnownUserException -> {
            ServerResponse.error("Unknown user, please try again", HttpStatusCode.Conflict.value)
        }

        is InvalidUserNameOrPasswordException -> {
            ServerResponse.error("Invalid username or password, please try again", HttpStatusCode.Conflict.value)
        }

        is ProductAlreadyInWishListException -> {
            ServerResponse.error("This product is  already in wish list", HttpStatusCode.Conflict.value)
        }

        is InvalidEmailException -> {
            ServerResponse.error("Email is not valid", HttpStatusCode.BadRequest.value)
        }

        is InvalidNameException -> {
            ServerResponse.error("Name is not valid", HttpStatusCode.BadRequest.value)
        }

        is EmailAlreadyExistException -> {
            ServerResponse.error("This Email already exist", 1001)
        }

        is InvalidPasswordInputException -> {
            ServerResponse.error(
                "Password should be at least has one letter, one special character, one number, and a total length between 8 to 14 characters",
                HttpStatusCode.BadRequest.value
            )
        }

        is InvalidUserNameInputException -> {
            ServerResponse.error(
                "The username must be 8 to 14 characters in length and should only include the dot (.) and underscore (_) characters.",
                HttpStatusCode.BadRequest.value
            )
        }

        is EmptyCartException -> {
            ServerResponse.error(
                "The Cart is Empty, please try again with some products",
                HttpStatusCode.NotFound.value
            )
        }

        is InvalidOrderIdException -> {
            ServerResponse.error("Invalid orderId", HttpStatusCode.BadRequest.value)
        }

        is ProductNotInSameCartMarketException -> {
            ServerResponse.error(
                "this product not in same market, you should delete cart",
                HttpStatusCode.BadRequest.value
            )
        }

        is InvalidStateOrderException -> {
            ServerResponse.error("State must be number from 1 to 4", HttpStatusCode.BadRequest.value)
        }

        is CantUpdateOrderStateException -> {
            ServerResponse.error("Can't update order state", HttpStatusCode.BadRequest.value)
        }

        is UnauthorizedException -> {
            ServerResponse.error("Unauthorized", HttpStatusCode.Forbidden.value)
        }

        is ImageNotFoundException -> {
            ServerResponse.error(
                "Image not found for userId", HttpStatusCode.BadRequest.value
            )
        }

        is AddImageFailedException -> {
            ServerResponse.error("Failed to Save Image", HttpStatusCode.BadRequest.value)
        }

        is InvalidOwnerIdException -> {
            ServerResponse.error("InvalidOwnerId", HttpStatusCode.BadRequest.value)
        }

        is InvalidPageNumberException -> {
            ServerResponse.error("Invalid Page Number", HttpStatusCode.NotFound.value)
        }

        is InvalidApiKeyException -> {
            ServerResponse.error("Invalid Api Key", HttpStatusCode.BadRequest.value)
        }

        is InvalidRuleException -> {
            ServerResponse.error("Invalid Token Rule", HttpStatusCode.Unauthorized.value)
        }

        is TokenExpiredException -> {
            ServerResponse.error("Expired Token", HttpStatusCode.Unauthorized.value)
        }

        is InvalidTokenException -> {
            ServerResponse.error("Invalid Token", HttpStatusCode.Unauthorized.value)
        }

        is InvalidTokenTypeException -> {
            ServerResponse.error("Invalid Token Type", HttpStatusCode.Unauthorized.value)
        }

        is MissingQueryParameterException -> {
            ServerResponse.error("Missing Query Parameter", HttpStatusCode.BadRequest.value)
        }

        is InvalidCountException -> {
            ServerResponse.error("Invalid Count", HttpStatusCode.BadRequest.value)
        }

        is InvalidPercentage -> {
            ServerResponse.error("Invalid Percentage", HttpStatusCode.BadRequest.value)
        }

        is InvalidExpirationDateException -> {
            ServerResponse.error(
                "Invalid Expiration Date, format should be yyyy-MM-dd HH:mm:ss",
                HttpStatusCode.BadRequest.value
            )
        }

        is InvalidCouponIdException -> {
            ServerResponse.error("Invalid Coupon Id", HttpStatusCode.BadRequest.value)
        }

        is InvalidCouponException -> {
            ServerResponse.error("Invalid Coupon", HttpStatusCode.BadRequest.value)
        }

        is CouponAlreadyClippedException -> {
            ServerResponse.error("Coupon Already Clipped", HttpStatusCode.BadRequest.value)
        }

        else -> {
            ServerResponse.error(cause.message.toString(), HttpStatusCode.InternalServerError.value)
        }
    }
    call.respond(messageResponse)
}