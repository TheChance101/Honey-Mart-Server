package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*

interface OrderDataSource {
    suspend fun createOrder(
        totalPrice: Double,
        products: List<OrderItem>,
        userId:Long
    ): Boolean

    suspend fun getAllOrdersForMarket(
        marketId: Long
    ): List<Order>

    suspend fun cancelOrder(
        orderId:Long
    ): Boolean

    //end region
    suspend fun isOrderExist(orderId: Long): Boolean
}