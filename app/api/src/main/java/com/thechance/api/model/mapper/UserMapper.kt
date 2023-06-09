package com.thechance.api.model.mapper

import com.thechance.api.model.UserModel
import com.thechance.core.entity.User


internal fun User.toApiUserModel(): UserModel {
    return UserModel(userId = userId, email = email, fullName = fullName, profileImage = profileImage)
}