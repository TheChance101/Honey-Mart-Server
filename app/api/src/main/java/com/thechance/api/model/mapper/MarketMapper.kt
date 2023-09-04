package com.thechance.api.model.mapper

import com.thechance.api.model.MarketInfoModel
import com.thechance.api.model.MarketRequestModel
import com.thechance.api.model.market.MarketApprovalModel
import com.thechance.api.model.market.MarketDetailsModel
import com.thechance.api.model.market.MarketModel
import com.thechance.core.entity.market.Market
import com.thechance.core.entity.market.MarketApproval
import com.thechance.core.entity.market.MarketDetails
import com.thechance.core.entity.market.MarketRequest


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

internal fun MarketRequest.toApiMarketRequestModel(): MarketRequestModel {
    return MarketRequestModel(
        marketId = marketId,
        marketName = marketName,
        imageUrl = imageUrl,
        description = description,
        address = address,
        isApproved = isApproved,
        ownerName = ownerName,
        ownerEmail = ownerEmail,
    )
}

internal fun MarketRequest.toApiMarketInfoModel(): MarketInfoModel {
    return MarketInfoModel(
        marketId = marketId,
        marketName = marketName,
        imageUrl = imageUrl,
        marketStatus = marketStatus,
        description = description,
        address = address,
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

internal fun MarketApproval.toApiMarketApprovalModel(): MarketApprovalModel {
    return MarketApprovalModel(
        marketId = marketId,
        isMarketApproved = isMarketApproved
    )
}