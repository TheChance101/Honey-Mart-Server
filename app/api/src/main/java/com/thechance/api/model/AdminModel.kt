package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class AdminModel(
    val tokens: TokensModel,
    val name: String
)
