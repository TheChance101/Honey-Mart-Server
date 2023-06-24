package com.thechance.core.entity.order

import com.thechance.core.entity.market.Market
import java.time.LocalDateTime

data class UserOrder(
    val orderId: Long,
    val totalPrice: Double,
    val date: LocalDateTime,
    val state: Int,
    val market: Market,
    val numItems: Long
)