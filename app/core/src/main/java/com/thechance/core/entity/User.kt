package com.thechance.core.entity

data class User(
    val userId: Long,
    val email: String,
    val fullName: String,
    val password: String,
    val salt: String
)
