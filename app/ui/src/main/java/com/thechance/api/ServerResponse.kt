package com.thechance.api

import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException

@kotlinx.serialization.Serializable
data class ServerResponse<T>(
    val value: T?,
    val message: String? = null,
    val isSuccess: Boolean = true
) {

    companion object{

        fun error(errorMessage: String): ServerResponse<String> {
            return ServerResponse(
                value = null,
                isSuccess = false,
                message = errorMessage,
            )
        }

        fun <T> success(result: T, successMessage: String? = null): ServerResponse<T> {
            return ServerResponse(
                value = result,
                isSuccess = true,
                message = successMessage
            )
        }

        fun handleError(error: Exception): ServerResponse<Nothing> {
            return when (error) {
                is InvalidInputException -> ServerResponse(
                    value = null,
                    isSuccess = false,
                    message = error.message
                )
                is IdNotFoundException -> ServerResponse(
                    value = null,
                    isSuccess = false,
                    message = error.message
                )
                is ItemNotAvailableException -> ServerResponse(
                    value = null,
                    isSuccess = false,
                    message = error.message
                )
                else -> ServerResponse(
                    value = null,
                    isSuccess = false,
                    message = error.message ?: "An unknown error occurred"
                )
            }
        }

    }

}
