package com.example.core.data.validation


enum class ErrorType(val value: Int, val message: String) {
    INVALID_INPUT(1, "Invalid input"),
    NOT_FOUND(404, "Id Not found "),
    DELETED_ITEM(2, "The item is no longer available.")
}

class Error(val error: ErrorType) : Throwable(error.message)

