package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketInfoModel(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val marketStatus: Boolean,
    val description: String,
    val address: String,
)
