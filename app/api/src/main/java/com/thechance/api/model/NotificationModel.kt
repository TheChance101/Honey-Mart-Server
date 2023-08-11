package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class NotificationModel(
    val id: String? = null,
    val title: String,
    val body: String,
    val date: Long? = null,
    val userId: String? = null,
    val tokens: List<String>
)
