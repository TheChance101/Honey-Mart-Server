package com.thechance.core.data.tables

import org.jetbrains.exposed.sql.Table

object OrderProductTable : Table() {
    val orderId = reference("orderId", OrderTable)
    val productId = reference("productId", ProductTable)
    val quantity = integer("quantity")
}