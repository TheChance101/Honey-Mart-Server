package com.thechance.core.data.datasource.database.tables.cart

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.ProductTable
import org.jetbrains.exposed.sql.Table


object CartProductTable : Table() {
    val cartId = reference("cartId", CartTable)
    val productId = reference("productId", ProductTable)
    val marketId = reference("marketId", MarketTable)
    val count = integer("count")
}