package com.thechance.core.data.datasource

import com.thechance.core.data.model.Order
import com.thechance.core.data.model.OrderItem

interface OrderDataSource {
    suspend fun createOrder(
        marketId:Long,
        orderDate:String,
        totalPrice: Double,
        isPaid: Boolean,
        products: List<OrderItem>
    ): Order

    suspend fun getAllOrdersForMarket(
        marketId:Long
    ):List<Order>
    //end region
}