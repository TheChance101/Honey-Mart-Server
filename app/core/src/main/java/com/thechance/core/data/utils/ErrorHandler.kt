package com.thechance.core.data.utils

class InvalidInputException(message: String = "this item not available") : Exception(message)

class IdNotFoundException(message: String = "this item not available") : Exception(message)

class ItemNotAvailableException(message: String = "this item not available") : Exception(message)

class InternalServiceErrorException(): Exception()

class MarketNameNotFound(message: String): Exception(message)

class ItemAlreadyDeleted(message: String = "already removed"): Exception(message)