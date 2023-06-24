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
    )
}

internal fun MarketDetails.toApiMarketDetailsModel(): MarketDetailsModel {
    return MarketDetailsModel(
        marketId = market.marketId,
        marketName = market.marketName,
        imageUrl = market.imageUrl,
        categories = categories.toApiCategoryModel()
    )
}