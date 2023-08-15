package com.thechance.api.model.mapper

import com.thechance.api.model.market.MarketDetailsModel
import com.thechance.api.model.market.MarketModel
import com.thechance.core.entity.market.Market
import com.thechance.core.entity.market.MarketDetails


internal fun Market.toApiMarketModel(): MarketModel {
    return MarketModel(
        marketId = marketId,
        marketName = marketName,
        imageUrl = imageUrl,
        description = description,
        latitude = latitude,
        longitude = longitude,
        address = address
    )
}

internal fun MarketDetails.toApiMarketDetailsModel(): MarketDetailsModel {
    return MarketDetailsModel(
        marketId = marketId,
        marketName = marketName,
        imageUrl = imageUrl,
        categoriesCount = categories.count(),
        productsCount = productsCount,
        description = description,
        latitude = latitude,
        longitude = longitude,
        address = address,
        categories = categories.toApiCategoryModel()
    )
}