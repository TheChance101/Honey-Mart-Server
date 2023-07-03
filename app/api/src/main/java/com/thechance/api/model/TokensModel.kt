package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TokensModel(
    val accessToken: String,
    val refreshToken: String
)
