package com.thechance.core.entity.market

data class MarketRequest(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val isDeleted: Boolean,
    val description: String,
    val address: String,
    val ownerName: String,
    val ownerEmail: String
)
