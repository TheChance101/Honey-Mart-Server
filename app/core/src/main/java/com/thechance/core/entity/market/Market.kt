package com.thechance.core.entity.market


data class Market(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val isDeleted: Boolean,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
