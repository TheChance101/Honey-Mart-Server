package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(val userId: Long, val fullName: String, val email: String)
