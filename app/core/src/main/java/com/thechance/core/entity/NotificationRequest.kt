package com.thechance.core.entity

data class NotificationRequest (
    val tokens: List<String>,
    val orderId: Long,
    val title: String,
    val body: String,
    val status: Int
)