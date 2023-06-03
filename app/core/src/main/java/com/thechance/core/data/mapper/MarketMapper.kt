package com.thechance.core.data.mapper

import com.thechance.api.model.Market
import com.thechance.core.data.tables.MarketTable
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toMarket(): Market {
    return Market(
        marketId = this[MarketTable.id].value,
        marketName = this[MarketTable.name],
    )
}