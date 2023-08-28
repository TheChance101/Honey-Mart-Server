package com.thechance.api.utils

import com.thechance.api.ServerResponse
import com.thechance.core.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun handleException(cause: Throwable, call: ApplicationCall) {
    val messageResponse = when (cause) {
        // User  Exceptions
        is InvalidInputException -> {
            ServerResponse.error("All fields are required.", 1002)
        }

        is UsernameAlreadyExistException -> {
            ServerResponse.error("User already exists.", 1003)
        }

        is InvalidUserIdException -> {
            ServerResponse.error("Invalid user ID.", 1004)
        }

        is InvalidUserNameOrPasswordException -> {
            ServerResponse.error("Invalid username or password, please try again.", 1005)
        }

        is InvalidEmailException -> {
            ServerResponse.error("Invalid email address.", 1006)
        }

        is InvalidNameException -> {
            ServerResponse.error("Invalid name.", 1007)
        }

        is EmailAlreadyExistException -> {
            ServerResponse.error("Email address already exists.", 1008)
        }

        is InvalidUserNameInputException -> {
            ServerResponse.error(
                "Username must be 8 to 14 characters in length and should only include the dot (.) and underscore (_) characters.",
                1009
            )
        }

        is InvalidPasswordInputException -> {
            ServerResponse.error(
                "Password should have at least one letter, one special character, one number, and a total length between 8 to 14 characters.",
                1010
            )
        }


        is UnKnownUserException -> {
            ServerResponse.error("Unknown user, please try again.", 1011)
        }

        // Owner Exception
        is InvalidOwnerIdException -> {
            ServerResponse.error("Invalid owner ID.", HttpStatusCode.BadRequest.value)
        }
        // Admin Exception
        is AdminAccessDeniedException -> {
            ServerResponse.error("Access denied.", HttpStatusCode.Forbidden.value)
        }
        // Market  Exceptions
        is InvalidMarketIdException -> {
            ServerResponse.error("Market not found with this ID.", HttpStatusCode.NotFound.value)
        }

        is InvalidMarketNameException -> {
            ServerResponse.error("Invalid market name", HttpStatusCode.NotFound.value)
        }

        is InvalidMarketDescriptionException -> {
            ServerResponse.error("Invalid description.", HttpStatusCode.NotFound.value)
        }

        is MarketDeletedException -> {
            ServerResponse.error("Market not found.", HttpStatusCode.NotFound.value)
        }

        // Category Exceptions
        is InvalidCategoryIdException -> {
            ServerResponse.error("Category not found with this ID.", HttpStatusCode.NotFound.value)
        }

        is InvalidCategoryNameException -> {
            ServerResponse.error("Invalid category name", HttpStatusCode.NotFound.value)
        }

        is CategoryDeletedException -> {
            ServerResponse.error("Category not found.", HttpStatusCode.NotFound.value)
        }

        is CategoryNameNotUniqueException -> {
            ServerResponse.error("Category name already exists in the market.", HttpStatusCode.BadRequest.value)
        }

        is NotValidCategoryList -> {
            ServerResponse.error("Invalid Categories", HttpStatusCode.BadRequest.value)
        }

        // Product Exceptions
        is InvalidProductIdException -> {
            ServerResponse.error("Product not found with this ID.", HttpStatusCode.NotFound.value)
        }

        is InvalidProductNameException -> {
            ServerResponse.error("Invalid product name.", HttpStatusCode.NotFound.value)
        }

        is InvalidProductDescriptionException -> {
            ServerResponse.error("Invalid product description.", HttpStatusCode.NotFound.value)
        }

        is InvalidProductPriceException -> {
            ServerResponse.error("Invalid product price.", HttpStatusCode.NotFound.value)
        }

        is ProductDeletedException -> {
            ServerResponse.error("Product not found.", HttpStatusCode.NotFound.value)
        }

        is ProductAlreadyInWishListException -> {
            ServerResponse.error("Product is  already exists in the WishList", HttpStatusCode.Conflict.value)
        }

        is ProductNotInSameCartMarketException -> {
            ServerResponse.error(
                "Product not in the same market., You should delete cart",
                HttpStatusCode.BadRequest.value
            )
        }
        // Order Exceptions
        is InvalidOrderIdException -> {
            ServerResponse.error("Invalid order ID.", HttpStatusCode.BadRequest.value)
        }

        is InvalidStateOrderException -> {
            ServerResponse.error(
                "Invalid order state. State must be a number from 1 to 6.",
                HttpStatusCode.BadRequest.value
            )
        }

        is CantUpdateOrderStateException -> {
            ServerResponse.error("Cannot update order state.", HttpStatusCode.BadRequest.value)
        }

        // Cart Exceptions
        is EmptyCartException -> {
            ServerResponse.error(
                "The cart is empty. Please try again with some products.",
                HttpStatusCode.NotFound.value
            )
        }

        is InvalidCountException -> {
            ServerResponse.error("Invalid Count.", HttpStatusCode.BadRequest.value)
        }

        is InvalidPercentage -> {
            ServerResponse.error("Invalid Percentage.", HttpStatusCode.BadRequest.value)
        }
        // Image Exceptions
        is InvalidImageIdException -> {
            ServerResponse.error("Image not found.", HttpStatusCode.NotFound.value)
        }

        is ImageNotFoundException -> {
            ServerResponse.error("Image not found for user ID.", HttpStatusCode.BadRequest.value)
        }

        is AddImageFailedException -> {
            ServerResponse.error("Failed to save image.", HttpStatusCode.BadRequest.value)
        }
        // Coupon  Exceptions
        is InvalidCouponIdException -> {
            ServerResponse.error("Invalid coupon ID.", HttpStatusCode.BadRequest.value)
        }

        is InvalidCouponException -> {
            ServerResponse.error("Invalid coupon.", HttpStatusCode.BadRequest.value)
        }

        is CouponAlreadyClippedException -> {
            ServerResponse.error("Coupon already clipped.", HttpStatusCode.BadRequest.value)
        }

        is InvalidExpirationDateException -> {
            ServerResponse.error(
                "Invalid Expiration Date, format should be yyyy-MM-dd HH:mm:ss",
                HttpStatusCode.BadRequest.value
            )
        }
        //other
        is IdNotFoundException -> {
            ServerResponse.error("ID not found", HttpStatusCode.NotFound.value)
        }

        is InvalidPageNumberException -> {
            ServerResponse.error("Invalid Page Number", HttpStatusCode.NotFound.value)
        }

        is MissingQueryParameterException -> {
            ServerResponse.error("Missing Query Parameter", HttpStatusCode.BadRequest.value)
        }
        // Unauthorized Exception
        is UnauthorizedException -> {
            ServerResponse.error("Unauthorized access.", HttpStatusCode.Forbidden.value)
        }

        is InvalidApiKeyException -> {
            ServerResponse.error("Invalid API key.", HttpStatusCode.BadRequest.value)
        }

        is InvalidTokenException -> {
            ServerResponse.error("Invalid token.", HttpStatusCode.Unauthorized.value)
        }

        is InvalidRuleException -> {
            ServerResponse.error("Invalid token rule.", HttpStatusCode.Unauthorized.value)
        }

        is TokenExpiredException -> {
            ServerResponse.error("Token expired.", HttpStatusCode.Unauthorized.value)
        }

        is InvalidTokenTypeException -> {
            ServerResponse.error("Invalid token type.", HttpStatusCode.Unauthorized.value)
        }

        is InvalidDeviceTokenException -> {
            ServerResponse.error("Invalid device token.", HttpStatusCode.BadRequest.value)
        }

        else -> {
            ServerResponse.error(cause.message.toString(), HttpStatusCode.InternalServerError.value)
        }
    }
    call.respond(messageResponse)
}