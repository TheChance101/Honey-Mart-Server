package com.thechance.core.data.datasource

import com.thechance.core.data.database.tables.ProductTable
import com.thechance.core.data.database.tables.order.OrderMarketTable
import com.thechance.core.data.database.tables.order.OrderProductTable
import com.thechance.core.data.database.tables.order.OrderTable
import com.thechance.core.data.model.OrderItem
import com.thechance.core.data.utils.dbQuery
import kotlinx.serialization.Serializable
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
        }
        true
    }

    data class OrderProducts(
        val orderId: Long,
        val products: List<OrderProduct>
    )

    data class OrderProduct(
        val productId: Long,
        val count: Int
    )

    @Serializable
    data class OrderWithPrice(
        val orderId: Long,
        val totalPrice: Double
    )

    override suspend fun getAllOrdersForMarket(
        marketId: Long
    ): List<OrderWithPrice> = dbQuery {
        val orderIds = OrderMarketTable.select { OrderMarketTable.marketId eq marketId }
            .map { it[OrderMarketTable.orderId].value }
        val orders = getMarketOrderProducts(orderIds, marketId)
        getOrdersWithTotalPricePerProduct(orders)
    }

    private fun getMarketOrderProducts(orderIds: List<Long>, marketId: Long): List<OrderProducts> {
        return orderIds.map { orderId ->
            OrderProducts(
                orderId,
                OrderProductTable
                    .select { (OrderProductTable.orderId eq orderId) and (OrderProductTable.marketId eq marketId) }
                    .map { OrderProduct(it[OrderProductTable.productId].value, it[OrderProductTable.count]) })
        }
    }

    private fun getOrdersWithTotalPricePerProduct(orders: List<OrderProducts>): List<OrderWithPrice> {
        return orders.map { order ->
            var totalPrice = 0.0
            order.products.forEach { product ->
                totalPrice += ProductTable.select { ProductTable.id eq product.productId }
                    .map { it[ProductTable.price] * product.count }.single()
            }
            OrderWithPrice(order.orderId, totalPrice)
        }
    }

    override suspend fun cancelOrder(orderId: Long) {
        dbQuery {
            OrderTable.update({ OrderTable.id eq orderId }) { tableRow ->
                tableRow[isCanceled] = true
            }
        }
    }
}