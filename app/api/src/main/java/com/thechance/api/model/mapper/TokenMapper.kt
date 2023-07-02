package com.thechance.api.model.mapper

import com.thechance.api.model.TokensModel
import com.thechance.core.data.security.token.Tokens

internal fun Tokens.toApiTokens(): TokensModel {
    return TokensModel(this.accessToken, this.refreshToken)
}