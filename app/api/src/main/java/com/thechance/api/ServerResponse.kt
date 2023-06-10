package com.thechance.api

@kotlinx.serialization.Serializable
data class ServerResponse<T>(
    val value: T?,
    val message: String? = null,
    val isSuccess: Boolean = true
) {

    companion object {

        fun error(errorMessage: String, errorType: String? = null): ServerResponse<String> {
            return ServerResponse(
                value = errorType,
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

    }

}
