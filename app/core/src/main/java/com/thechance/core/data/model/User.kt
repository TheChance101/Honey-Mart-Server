package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Long,
    val userName: String,
    val password: String,
    val salt: String
)
