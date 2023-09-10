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
            ServerResponse.error("Invalid owner ID.", 1022)
        }
        // Admin Exception
        is AdminAccessDeniedException -> {
            ServerResponse.error("Access denied.", 1032)
        }
        // Market  Exceptions
        is InvalidMarketIdException -> {
            ServerResponse.error("Market not found with this ID.", 1042)
        }

        is InvalidMarketNameException -> {
            ServerResponse.error("Invalid market name", 1043)
        }

        is InvalidMarketDescriptionException -> {
            ServerResponse.error("Invalid description.", 1044)
        }

        is MarketDeletedException -> {
            ServerResponse.error("Market not found.", 1045)
        }

        is MarketAlreadyExistException -> {
            ServerResponse.error("Market already exist.", 1046)
        }

        is MarketNotApprovedException -> {
            ServerResponse.error("Market not approved.", 1047)
        }

        // Category Exceptions
        is InvalidCategoryIdException -> {
            ServerResponse.error("Category not found with this ID.", 1052)
        }

        is InvalidCategoryNameException -> {
            ServerResponse.error("Invalid category name", 1053)
        }

        is CategoryDeletedException -> {
            ServerResponse.error("Category not found.", 1054)
        }

        is CategoryNameNotUniqueException -> {
            ServerResponse.error("Category name already exists in the market.", 1055)
        }

        is NotValidCategoryList -> {
            ServerResponse.error("Invalid Categories", 1056)
        }

        // Product Exceptions
        is InvalidProductIdException -> {
            ServerResponse.error("Product not found with this ID.", 1062)
        }

        is InvalidProductNameException -> {
            ServerResponse.error("Invalid product name.", 1063)
        }

        is InvalidProductDescriptionException -> {
            ServerResponse.error("Invalid product description.", 1064)
        }

        is InvalidProductPriceException -> {
            ServerResponse.error("Invalid product price.", 1065)
        }

        is ProductDeletedException -> {
            ServerResponse.error("Product not found.", 1066)
        }

        is ProductAlreadyInWishListException -> {
            ServerResponse.error("Product is  already exists in the WishList", 1067)
        }

        is ProductNotInSameCartMarketException -> {
            ServerResponse.error(
                "Product not in the same market., You should delete cart",
                1068
            )
        }
        // Order Exceptions
        is InvalidOrderIdException -> {
            ServerResponse.error("Invalid order ID.", 1072)
        }

        is InvalidStateOrderException -> {
            ServerResponse.error(
                "Invalid order state. State must be a number from 1 to 6.",
                1073
            )
        }

        is CantUpdateOrderStateException -> {
            ServerResponse.error("Cannot update order state.", 1074)
        }

        // Cart Exceptions
        is EmptyCartException -> {
            ServerResponse.error(
                "The cart is empty. Please try again with some products.",
                1082
            )
        }

        is CountInvalidInputException -> {
            ServerResponse.error("Invalid Count.", 1083)
        }

        is InvalidPercentage -> {
            ServerResponse.error("Invalid Percentage.", 1084)
        }
        // Image Exceptions
        is InvalidImageIdException -> {
            ServerResponse.error("Image not found.", 1092)
        }

        is ImageNotFoundException -> {
            ServerResponse.error("Image not found for user ID.", 1093)
        }

        is AddImageFailedException -> {
            ServerResponse.error("Failed to save image.", 1094)
        }
        // Coupon  Exceptions
        is InvalidCouponIdException -> {
            ServerResponse.error("Invalid coupon ID.", 1102)
        }

        is InvalidCouponException -> {
            ServerResponse.error("Invalid coupon.", 1103)
        }

        is CouponAlreadyClippedException -> {
            ServerResponse.error("Coupon already clipped.", 1104)
        }

        is InvalidExpirationDateException -> {
            ServerResponse.error(
                "Invalid Expiration Date, format should be yyyy-MM-dd HH:mm:ss",
                1105
            )
        }

        is InvalidCountException -> {
            ServerResponse.error("Invalid Count.", 1106)
        }

        // Unauthorized Exception
        is UnauthorizedException -> {
            ServerResponse.error("Unauthorized access.", 1112)
        }

        is InvalidApiKeyException -> {
            ServerResponse.error("Invalid API key.", 1113)
        }

        is InvalidTokenException -> {
            ServerResponse.error("Invalid token.", 1114)
        }

        is InvalidRuleException -> {
            ServerResponse.error("Invalid token rule.", 1115)
        }

        is TokenExpiredException -> {
            ServerResponse.error("Token expired.", 1116)
        }

        is InvalidTokenTypeException -> {
            ServerResponse.error("Invalid token type.", 1117)
        }

        is InvalidDeviceTokenException -> {
            ServerResponse.error("Invalid device token.", 1118)
        }

        is IdNotFoundException -> {
            ServerResponse.error("ID not found", 1119)
        }

        is InvalidPageNumberException -> {
            ServerResponse.error("Invalid Page Number", 1120)
        }

        is MissingQueryParameterException -> {
            ServerResponse.error("Missing Query Parameter", 1121)
        }

        //Reviews Exceptions
        is InvalidRatingException -> {
            ServerResponse.error("Rating should be between 1 and 5", 1131)
        }

        is InvalidReviewContentException -> {
            ServerResponse.error("Content length should be between 6 and 600 chars", 1132)
        }

        else -> {
            ServerResponse.error(cause.message.toString(), HttpStatusCode.InternalServerError.value)
        }
    }
    call.respond(messageResponse)
}