package com.thechance.core.data.datasource.database.tables.order

import com.thechance.core.data.datasource.database.tables.ProductTable
import org.jetbrains.exposed.sql.Table

object OrderProductTable : Table() {
    val orderId = reference("orderId", OrderTable)
    val productId = reference("productId", ProductTable)
    val count = integer("count")
}