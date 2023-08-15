package com.thechance.core.entity.market

import com.thechance.core.entity.Category

data class MarketDetails(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val isDeleted: Boolean,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val productsCount: Int,
    val categories: List<Category>
)