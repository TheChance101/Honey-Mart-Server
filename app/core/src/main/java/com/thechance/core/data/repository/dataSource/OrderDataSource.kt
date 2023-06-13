package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Order
import com.thechance.core.entity.OrderDetails
import com.thechance.core.entity.OrderItem

interface OrderDataSource {
    suspend fun createOrder(
        userId: Long,
        marketId: Long,
        products: List<OrderItem>,
        totalPrice: Double
    ): Boolean

    suspend fun getAllOrdersForMarket(
        marketId: Long
    ): List<Order>

    //end region
    suspend fun isOrderExist(orderId: Long): Boolean
    suspend fun updateOrderState(orderId: Long, newState: Int): Boolean
    suspend fun getAllOrdersForUser(userId: Long): List<Order>
    suspend fun getOrderById(orderId: Long): OrderDetails
}