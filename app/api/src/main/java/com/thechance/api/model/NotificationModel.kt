package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationModel(
    val notificationId: Long,
    val userId: Long,
    val orderId: Long,
    val title: String,
    val body: String,
    val date: Long,
)