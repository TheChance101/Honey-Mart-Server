package com.thechance.core.entity

import java.time.LocalDate

data class OrderDetails(
    val orderId: Long,
    val userId: Long,
    val marketId: Long,
    val products: List<ProductInOrder>,
    val totalPrice: Double,
    val date: LocalDate,
    val state: Int
)
