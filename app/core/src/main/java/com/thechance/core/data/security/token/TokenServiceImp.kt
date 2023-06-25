package com.thechance.core.data.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thechance.core.data.repository.security.TokenService
import com.thechance.core.utils.ACCESS_TOKEN
import com.thechance.core.utils.REFRESH_TOKEN
import com.thechance.core.utils.TOKEN_TYPE
import org.koin.core.component.KoinComponent
import java.util.*

class TokenServiceImp : TokenService, KoinComponent {

    override fun generateTokens(config: TokenConfig, subject: String, vararg claims: TokenClaim) = Tokens(
        refreshToken = generateRefreshToken(config, subject, *claims),
        accessToken = generateAccessToken(config, subject, *claims)
    )

    override fun generateAccessToken(config: TokenConfig, subject: String, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withSubject(subject)
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.accessTokenExpiresIn))
            .withClaim(TOKEN_TYPE, ACCESS_TOKEN)
        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }

    override fun generateRefreshToken(config: TokenConfig, subject: String, vararg claims: TokenClaim): String {
        var refreshToken = JWT.create()
            .withSubject(subject)
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.refreshTokenExpiresIn))
            .withClaim(TOKEN_TYPE, REFRESH_TOKEN)
        claims.forEach { claim ->
            refreshToken = refreshToken.withClaim(claim.name, claim.value)
        }
        return refreshToken.sign(Algorithm.HMAC256(config.secret))
    }
}