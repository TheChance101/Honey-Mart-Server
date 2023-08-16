package com.thechance.core.entity

import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val timeStamp: LocalDateTime,
    val receiverId: Long
)
