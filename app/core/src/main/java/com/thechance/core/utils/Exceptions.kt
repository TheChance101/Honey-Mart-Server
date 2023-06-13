package com.thechance.core.utils

class InvalidInputException : Exception()

class InvalidCategoryNameException : Exception()
class InvalidMarketNameException : Exception()
class InvalidProductNameException : Exception()
class InvalidProductQuantityException : Exception()
class InvalidProductPriceException : Exception()
class InvalidOrderTotalPriceException : Exception()

class InvalidCategoryNameLettersException : Exception()
class InvalidMarketIdException : Exception()
class InvalidCategoryIdException : Exception()
class InvalidProductIdException : Exception()

class InvalidImageIdException : Exception()

class IdNotFoundException : Exception()

class ItemNotAvailableException : Exception()

//Delete
class MarketDeletedException : Exception()

class CategoryDeletedException : Exception()

class ProductDeletedException : Exception()

class NotValidCategoryList : Exception()

class CategoryNameNotUniqueException : Exception()

//region user
class InvalidUserIdException : Exception()
class UsernameAlreadyExistException : Exception()
class UnKnownUserException : Exception()
class InvalidUserNameOrPasswordException : Exception()
class InvalidEmailException : Exception()
class InvalidNameException : Exception()
class EmailAlreadyExistException : Exception()

class InvalidPasswordInputException : Exception()
class InvalidUserNameInputException : Exception()

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

//endregion