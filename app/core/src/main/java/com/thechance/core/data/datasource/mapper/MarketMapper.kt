package com.thechance.core.data.datasource.mapper

import com.thechance.core.entity.Market
import com.thechance.core.data.datasource.database.tables.MarketTable
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toMarket(): Market {
    return Market(
        marketId = this[MarketTable.id].value,
        marketName = this[MarketTable.name],
        imageUrl = this[MarketTable.imageUrl]
    )
}