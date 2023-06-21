package com.thechance.core.entity.order

import com.thechance.core.entity.User
import java.time.LocalDateTime


data class MarketOrder(
    val orderId: Long,
    val totalPrice: Double,
    val date: LocalDateTime,
    val state: Int,
    val user: User
)