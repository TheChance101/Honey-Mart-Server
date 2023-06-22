package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.order.OrderTable
import com.thechance.core.entity.order.UserOrder
import org.jetbrains.exposed.sql.ResultRow


internal fun ResultRow.toUserOrder(itemsCount: Long): UserOrder {
    return UserOrder(
        orderId = this[OrderTable.id].value,
        totalPrice = this[OrderTable.totalPrice],
        date = this[OrderTable.orderDate],
        state = this[OrderTable.state],
        market = this.toMarket(),
        numItems = itemsCount
    )
}
