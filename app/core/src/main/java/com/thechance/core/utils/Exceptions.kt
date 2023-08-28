package com.thechance.core.utils

class InvalidInputException : Exception()

class UnauthorizedException : Exception()
class InvalidCategoryNameException : Exception()
class InvalidMarketNameException : Exception()
class InvalidProductNameException : Exception()
class InvalidProductDescriptionException : Exception()
class InvalidCountException : Exception()
class InvalidPercentage : Exception()
class InvalidExpirationDateException : Exception()
class InvalidCouponIdException : Exception()
class InvalidCouponException : Exception()
class CouponAlreadyClippedException : Exception()
class InvalidMarketDescriptionException : Exception()
class InvalidProductPriceException : Exception()
class InvalidMarketIdException : Exception()
class InvalidCategoryIdException : Exception()
class InvalidProductIdException : Exception()

class InvalidImageIdException : Exception()

class IdNotFoundException : Exception()

//Delete
class MarketDeletedException : Exception()

class CategoryDeletedException : Exception()

class ProductDeletedException : Exception()

class NotValidCategoryList : Exception()

class MissingQueryParameterException : Exception()

class CategoryNameNotUniqueException : Exception()

//region user
class InvalidUserIdException : Exception()
class AdminAccessDeniedException : Exception()
class UsernameAlreadyExistException : Exception()
class UnKnownUserException : Exception()
class InvalidUserNameOrPasswordException : Exception()
class InvalidEmailException : Exception()
class InvalidNameException : Exception()
class EmailAlreadyExistException : Exception()

class InvalidPasswordInputException : Exception()
class InvalidUserNameInputException : Exception()
class AddImageFailedException : Exception()
class ImageNotFoundException : Exception()

//endregion

class InvalidOrderIdException : Exception()
class EmptyCartException : Exception()

class ProductAlreadyInWishListException : Exception()

//region cart
class CountInvalidInputException : Exception()

class ProductNotInSameCartMarketException : Exception()
//endregion

//region owner
class InvalidOwnerIdException : Exception()
class InvalidStateOrderException : Exception()

class CantUpdateOrderStateException : Exception()
class InvalidApiKeyException : Exception()

class InvalidPageNumberException : Exception()

//endregion
//auth region
class InvalidRuleException : Exception()
class TokenExpiredException : Exception()
class InvalidTokenException : Exception()
class InvalidTokenTypeException : Exception()
class InvalidDeviceTokenException : Exception()
//end region