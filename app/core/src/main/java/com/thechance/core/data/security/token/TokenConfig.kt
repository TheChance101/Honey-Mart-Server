package com.thechance.core.data.security.token

import org.koin.core.component.KoinComponent

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String
):KoinComponent
