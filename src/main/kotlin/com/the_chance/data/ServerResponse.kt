package com.the_chance.data

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

    }

}
