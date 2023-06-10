package com.thechance.core.data.datasource

import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.database.tables.order.OrderMarketTable
import com.thechance.core.data.database.tables.order.OrderProductTable
import com.thechance.core.data.database.tables.order.OrderTable
import com.thechance.core.data.model.OrderItem
import com.thechance.core.data.model.Order
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.*

class OrderDataSourceImp : OrderDataSource {
    override suspend fun createOrder(
        totalPrice: Double,
        products: List<OrderItem>,
        userId: Long
    ): Boolean = dbQuery {
        val newOrder = OrderTable.insert {
            it[this.totalPrice] = totalPrice
            it[this.userId] = userId
        }
        OrderProductTable.batchInsert(products) { orderItem ->
            this[OrderProductTable.orderId] = newOrder[OrderTable.id]
            this[OrderProductTable.productId] = orderItem.productId
            this[OrderProductTable.count] = orderItem.count
            this[OrderProductTable.marketId] = orderItem.marketId
        }
        OrderMarketTable.batchInsert(products) { orderItem ->
            this[OrderMarketTable.orderId] = newOrder[OrderTable.id]
            this[OrderMarketTable.marketId] = orderItem.marketId
            this[OrderMarketTable.isCanceled] = newOrder[OrderTable.isCanceled]
        }
        true
    }

    override suspend fun getAllOrdersForMarket(marketId: Long): List<Order> = dbQuery {
        OrderMarketTable
            .join(OrderProductTable, JoinType.INNER, additionalConstraint = {
                OrderMarketTable.orderId eq
                        OrderProductTable.orderId and (OrderMarketTable.marketId eq
                        marketId)
            })
            .select { OrderMarketTable.isCanceled eq false }
            .groupBy { it[OrderMarketTable.orderId].value }
            .map { (_, orderRows) ->
                val orderId = orderRows.first()[OrderMarketTable.orderId].value
                val totalPrice = orderRows.sumOf {
                    val productId = it[OrderProductTable.productId].value
                    val count = it[OrderProductTable.count]
                    val price = ProductTable
                        .select { ProductTable.id eq productId }
                        .singleOrNull()?.get(ProductTable.price) ?: 0.0
                    price * count
                }
                Order(orderId, totalPrice)
            }
    }


    override suspend fun cancelOrder(orderId: Long): Boolean = dbQuery {
        OrderTable.update({ OrderTable.id eq orderId }) { tableRow ->
            tableRow[isCanceled] = true
        }

        OrderMarketTable.update({ OrderMarketTable.orderId eq orderId }) { tableRow ->
            tableRow[isCanceled] = true
        }
        true
    }

    override suspend fun isOrderExist(orderId: Long): Boolean =
        dbQuery {
            OrderTable.select { OrderTable.id eq orderId }.singleOrNull() != null
        }
}