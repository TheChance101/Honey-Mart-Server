package com.thechance.api.model.mapper

import com.thechance.api.model.AdminModel
import com.thechance.core.entity.Admin

fun Admin.toApiAdmin():AdminModel {
    return AdminModel(
        tokens = tokens.toApiTokens(),
        name = name
    )
}