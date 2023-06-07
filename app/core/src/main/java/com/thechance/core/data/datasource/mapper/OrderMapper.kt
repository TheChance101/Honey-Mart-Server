package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.model.Order
import com.thechance.core.data.tables.OrderTable
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toOrder(): Order {
    return Order(
        id = this[OrderTable.id].value,
        marketId = this[OrderTable.marketId],
        orderDate = this[OrderTable.orderDate],
        totalPrice = this[OrderTable.totalPrice],
        isPaid = this[OrderTable.isPaid],
        isCanceled = this[OrderTable.isCanceled]
    )
}