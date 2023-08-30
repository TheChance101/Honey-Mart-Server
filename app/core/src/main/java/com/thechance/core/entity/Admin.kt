package com.thechance.core.entity

import com.thechance.core.data.security.token.Tokens

data class Admin(
    val tokens: Tokens,
    val name: String
)
