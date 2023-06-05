package com.thechance.core.data.utils

class InvalidInputException : Exception()
class IdNotFoundException : Exception()
class ItemNotAvailableException : Exception()
class InternalServiceErrorException : Exception()
class MarketNameNotFound : Exception()
class ItemAlreadyDeleted : Exception()
class MarketNameLengthException : Exception()
class MarketNameWithSymbolException: Exception()
class MarketItemDeletedException: Exception()

class ProductUpdateException: Exception()
class ProductNameLengthException: Exception()
class ProductEmptyNameException: Exception()
class ProductEmptyQuantityException: Exception()
class ProductQuantityLengthException: Exception()
class ProductPriceRangeException: Exception()
class ProductEmptyPriceException: Exception()
class ProductAssignCategoryException: Exception()

class CategoryNotFoundException: Exception()