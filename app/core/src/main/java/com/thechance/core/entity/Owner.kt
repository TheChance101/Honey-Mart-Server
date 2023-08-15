package com.thechance.core.entity

import com.thechance.core.data.security.token.Tokens

data class Owner(val ownerId: Long, val email: String,val fullName: String, val password: String, val salt: String)
data class OwnerTokens(
    val fullName: String,
    val tokens: Tokens
)