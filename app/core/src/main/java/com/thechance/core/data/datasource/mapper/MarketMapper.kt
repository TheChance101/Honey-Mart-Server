package com.thechance.core.data.datasource.mapper

import com.thechance.core.entity.market.Market
import com.thechance.core.data.datasource.database.tables.MarketTable
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toMarket(): Market {
    return Market(
        marketId = this[MarketTable.id].value,
        marketName = this[MarketTable.name],
        imageUrl = this[MarketTable.imageUrl],
        isDeleted = this[MarketTable.isDeleted],
        description = this[MarketTable.description],
        latitude = this[MarketTable.latitude],
        longitude = this[MarketTable.longitude],
        address = this[MarketTable.address],
        isApproved = this[MarketTable.isApproved]
    )
}