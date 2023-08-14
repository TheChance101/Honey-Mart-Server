package com.thechance.api.model.mapper

import com.thechance.api.model.OwnerModel
import com.thechance.api.model.OwnerTokensModel
import com.thechance.core.entity.Owner
import com.thechance.core.entity.OwnerTokens

internal fun Owner.toApiOwnerModel(): OwnerModel {
    return OwnerModel(
        ownerId = ownerId,
        fullName = fullName,
        email = email
    )
}
internal fun OwnerTokens.toApiOwnerTokens(): OwnerTokensModel {
    return OwnerTokensModel(this.fullName, this.tokens.toApiTokens())
}