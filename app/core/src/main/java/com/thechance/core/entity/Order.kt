package com.thechance.core.entity

import java.time.LocalDate

data class Order(
    val orderId: Long,
    val userId: Long,
    val marketId: Long,
    val totalPrice: Double,
    val date: LocalDate,
    val state: Int
)