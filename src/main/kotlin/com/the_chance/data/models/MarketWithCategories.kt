package com.the_chance.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MarketWithCategories(
    val marketId: Long,
    val marketName: String,
    val categories: List<Category>
)
