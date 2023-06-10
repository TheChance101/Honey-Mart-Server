package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(val userId: Long, val userName: String, val password: String, val salt: String)
