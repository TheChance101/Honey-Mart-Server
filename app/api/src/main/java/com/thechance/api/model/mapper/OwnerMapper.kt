package com.thechance.api.model.mapper

import com.thechance.api.model.OwnerModel
import com.thechance.core.entity.Owner

internal fun Owner.toApiOwnerModel(): OwnerModel {
    return OwnerModel(
        ownerId = ownerId,
        fullName = fullName,
        email = email
    )
}