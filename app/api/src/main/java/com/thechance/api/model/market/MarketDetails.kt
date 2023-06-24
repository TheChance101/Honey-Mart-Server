package com.thechance.api.model.market

import com.thechance.api.model.CategoryModel
import kotlinx.serialization.Serializable


@Serializable
data class MarketDetailsModel(
    val marketId: Long,
    val marketName: String,
    val imageUrl: String,
    val categories: List<CategoryModel>
)