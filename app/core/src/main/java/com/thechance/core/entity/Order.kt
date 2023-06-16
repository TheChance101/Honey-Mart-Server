package com.thechance.core.entity

import org.jetbrains.exposed.sql.javatime.timestamp
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

data class Order(
    val orderId: Long, val userId: Long, val marketId: Long, val totalPrice: Double, val date: LocalDateTime, val state: Int
)