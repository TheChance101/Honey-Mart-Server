package com.thechance.api.mapper

import com.thechance.api.model.MarketModel
import com.thechance.core.entity.Market


internal fun Market.toApiMarketModel(): MarketModel {
    return MarketModel(
        marketId = marketId,
        marketName = marketName
    )
}