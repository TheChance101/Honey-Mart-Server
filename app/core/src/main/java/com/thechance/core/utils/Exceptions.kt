package com.thechance.core.utils

//region User
class InvalidInputException : Exception()
class UsernameAlreadyExistException : Exception()
class InvalidUserIdException : Exception()
class InvalidUserNameOrPasswordException : Exception()
class InvalidEmailException : Exception()
class InvalidNameException : Exception()
class EmailAlreadyExistException : Exception()
class InvalidUserNameInputException : Exception()
class InvalidPasswordInputException : Exception()
class UnKnownUserException : Exception()
//endregion

//region Owner
class InvalidOwnerIdException : Exception()
//endregion

//region Admin
class AdminAccessDeniedException : Exception()
//endregion

//region Market
class InvalidMarketIdException : Exception()
class MarketAlreadyExistException : Exception()
class InvalidMarketNameException : Exception()
class InvalidMarketDescriptionException : Exception()
class MarketDeletedException : Exception()
//endregion

//region Category
class InvalidCategoryIdException : Exception()
class InvalidCategoryNameException : Exception()
class CategoryDeletedException : Exception()
class CategoryNameNotUniqueException : Exception()
class NotValidCategoryList : Exception()
//endregion

//region Product
class InvalidProductIdException : Exception()
class InvalidProductNameException : Exception()
class InvalidProductDescriptionException : Exception()
class InvalidProductPriceException : Exception()
class ProductDeletedException : Exception()
class ProductAlreadyInWishListException : Exception()
class ProductNotInSameCartMarketException : Exception()
//endregion

//region Order
class InvalidOrderIdException : Exception()
class InvalidStateOrderException : Exception()
class CantUpdateOrderStateException : Exception()
//endregion

//region Cart
class EmptyCartException : Exception()
class CountInvalidInputException : Exception()
class InvalidPercentage : Exception()
//endregion

//region Image
class InvalidImageIdException : Exception()
class ImageNotFoundException : Exception()
class AddImageFailedException : Exception()
//endregion

//region Coupon
class InvalidCouponIdException : Exception()
class InvalidCouponException : Exception()
class CouponAlreadyClippedException : Exception()
class InvalidExpirationDateException : Exception()
class InvalidCountException : Exception()
//endregion

//region Exceptions
class IdNotFoundException : Exception()
class InvalidPageNumberException : Exception()
class MissingQueryParameterException : Exception()

//endregion

//region Authentication
class UnauthorizedException : Exception()
class InvalidRuleException : Exception()
class TokenExpiredException : Exception()
class InvalidTokenException : Exception()
class InvalidTokenTypeException : Exception()
class InvalidDeviceTokenException : Exception()
class InvalidApiKeyException : Exception()
//endregion