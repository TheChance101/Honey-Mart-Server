package com.thechance.core.data.database.tables.order

import com.thechance.core.data.database.tables.MarketTable
import org.jetbrains.exposed.sql.Table

object OrderMarketTable : Table() {
    val orderId = reference("orderId", OrderTable)
    val marketId = reference("marketId", MarketTable)
}