package com.thechance.core.data.database.tables

import org.jetbrains.exposed.sql.Table


object CartProductTable : Table() {
    val cartId = reference("cartId", CartTable)
    val productId = reference("productId", ProductTable)
    val marketId = reference("marketId", MarketTable)
    val count = integer("count")
}