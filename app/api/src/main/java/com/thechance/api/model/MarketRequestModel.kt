package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketRequestModel(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val description: String,
    val address: String,
    val ownerName: String,
    val ownerEmail: String
)
