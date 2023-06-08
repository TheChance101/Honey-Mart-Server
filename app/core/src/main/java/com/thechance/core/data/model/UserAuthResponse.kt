package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserAuthResponse(
    val token: String
)
