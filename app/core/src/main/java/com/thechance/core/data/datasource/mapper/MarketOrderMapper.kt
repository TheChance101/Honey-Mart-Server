package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.entity.order.MarketOrder
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toMarketOrder(): MarketOrder {
    return MarketOrder(
        orderId = this[OrderTable.id].value,
        totalPrice = this[OrderTable.totalPrice],
        date = this[OrderTable.orderDate],
        state = this[OrderTable.state],
        user = this.toUser()
    )
}
