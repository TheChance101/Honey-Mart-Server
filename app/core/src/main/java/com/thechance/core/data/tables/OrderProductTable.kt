package com.thechance.core.data.tables

import com.thechance.core.data.database.tables.ProductTable
import org.jetbrains.exposed.sql.Table

object OrderProductTable : Table() {
    val orderId = reference("orderId", OrderTable)
    val productId = reference("productId", ProductTable)
    val quantity = integer("quantity")
}