package com.thechance.api.utils

import io.ktor.http.*

class InvalidInputException(message: String, val statusCode: HttpStatusCode = HttpStatusCode.BadRequest) :
    Exception(message)

class IdNotFoundException(message: String, val statusCode: HttpStatusCode = HttpStatusCode.NotFound) :
    Exception(message)

class ItemNotAvailableException(message: String, val statusCode: HttpStatusCode = HttpStatusCode.NotFound) :
    Exception(message)