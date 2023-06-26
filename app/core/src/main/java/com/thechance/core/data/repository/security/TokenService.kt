package com.thechance.core.data.repository.security

import com.thechance.core.data.security.token.TokenClaim
import com.thechance.core.data.security.token.TokenConfig
import com.thechance.core.data.security.token.Tokens
import java.util.*

interface TokenService {
    fun generateAccessToken(config: TokenConfig, subject: String, vararg claims: TokenClaim): String
    fun generateRefreshToken(config: TokenConfig, subject: String, vararg claims: TokenClaim): String
    fun generateTokens(config: TokenConfig, subject: String, vararg claims: TokenClaim): Tokens
    fun verifyToken(token: String): String
    fun getTokenExpiration(token: String): Date
    fun verifyTokenType(token: String): String
}