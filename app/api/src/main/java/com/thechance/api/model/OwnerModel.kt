package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class OwnerModel(val ownerId: Long, val fullName: String, val email: String)

@Serializable
data class OwnerTokensModel(val fullName: String, val marketId: Long?, val tokens: TokensModel)
