package com.thechance.core.data.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.thechance.core.data.repository.security.TokenService
import org.koin.core.component.KoinComponent
import java.util.*

class TokenServiceImp : TokenService, KoinComponent {
    override fun generate(config: TokenConfig, subject: String, vararg claims: TokenClaim): String {
        var token = JWT.create()
            .withSubject(subject)
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
        claims.forEach { claim ->
            token = token.withClaim(claim.name, claim.value)
        }

        return token.sign(Algorithm.HMAC256(config.secret))
    }
}