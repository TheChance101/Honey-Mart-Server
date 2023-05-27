package com.the_chance.data.services.validation


enum class ErrorType(val value: Int, val message: String) {
    INVALID_INPUT(1, "Invalid input"),
    NOT_FOUND(404, "Id Not found "),
}

class Error(val error: ErrorType) : Throwable(error.message)

