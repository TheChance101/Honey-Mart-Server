package com.thechance.core.data.repository.security

import com.thechance.core.data.security.token.TokenClaim
import com.thechance.core.data.security.token.TokenConfig

interface TokenService {
    fun generate(config: TokenConfig, subject: String, vararg claims: TokenClaim): String
}