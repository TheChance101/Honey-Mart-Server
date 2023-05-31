package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class MarketWithCategories(
    val marketId: Long,
    val marketName: String,
    val categories: List<Category>
)
