package com.thechance.core.data.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.koin.core.component.KoinComponent


class TokenVerifier(private val config: TokenConfig) : KoinComponent {
    fun getVerifier(): JWTVerifier = JWT.require(Algorithm.HMAC256(config.secret))
        .withIssuer(config.issuer)
        .build()
}