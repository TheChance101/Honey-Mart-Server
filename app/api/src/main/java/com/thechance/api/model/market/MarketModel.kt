package com.thechance.api.model.market

import kotlinx.serialization.Serializable

@Serializable
data class MarketModel(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
