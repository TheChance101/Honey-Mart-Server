package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Owner(
    val ownerId: Long,
    val userName: String,
    val password: String,
)
