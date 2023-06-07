package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toOrder
import com.thechance.core.data.model.Order
import com.thechance.core.data.model.OrderItem
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.OrderProductTable
import com.thechance.core.data.tables.OrderTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class OrderDataSourceImp : OrderDataSource {
    override suspend fun createOrder(
        marketId: Long,
        orderDate: String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>
    ): Order = dbQuery {
        val newOrder = OrderTable.insert {
            it[this.marketId] = marketId
            it[this.orderDate] = orderDate
            it[this.totalPrice] = totalPrice
            it[this.isPaid] = isPaid
        }
        OrderProductTable.batchInsert(products) { orderItem ->
            this[OrderProductTable.orderId] = newOrder[OrderTable.id]
            this[OrderProductTable.productId] = orderItem.productId
            this[OrderProductTable.quantity] = orderItem.quantity
        }
        Order(
            id = newOrder[OrderTable.id].value,
            marketId = newOrder[OrderTable.marketId],
            orderDate = newOrder[OrderTable.orderDate],
            totalPrice = newOrder[OrderTable.totalPrice],
            isPaid = newOrder[OrderTable.isPaid]
        )
    }

    override suspend fun getAllOrdersForMarket(
        marketId: Long
    ): List<Order> = dbQuery {
        OrderTable.select { OrderTable.marketId eq marketId }
            .map { it.toOrder() }
    }
}