package com.thechance.core.entity

import java.time.LocalDateTime

data class Order(
    val orderId: Long, val userId: Long, val marketId: Long, val totalPrice: Double, val date: LocalDateTime, val state: Int
)