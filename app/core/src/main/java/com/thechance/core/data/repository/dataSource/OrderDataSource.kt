package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.order.MarketOrder
import com.thechance.core.entity.order.OrderDetails
import com.thechance.core.entity.order.OrderItem
import com.thechance.core.entity.order.UserOrder

interface OrderDataSource {

    suspend fun createOrder(
        userId: Long, marketId: Long, products: List<OrderItem>, totalPrice: Double
    ): Boolean

    suspend fun getOrdersForMarket(marketId: Long, state: Int): List<MarketOrder>

    suspend fun getAllOrdersForMarket(marketId: Long): List<MarketOrder>

    suspend fun isOrderExist(orderId: Long): Boolean

    suspend fun updateOrderState(orderId: Long, newState: Int): Boolean

    suspend fun getOrdersForUser(userId: Long, state: Int): List<UserOrder>

    suspend fun getAllOrdersForUser(userId: Long): List<UserOrder>

    suspend fun getOrderById(orderId: Long): OrderDetails

    suspend fun getOrderState(orderId: Long): Int

    suspend fun getUserLatestOrderId(userId: Long): Long?
}