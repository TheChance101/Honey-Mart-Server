package com.thechance.api.mapper

import com.thechance.api.model.UserModel
import com.thechance.core.entity.User


internal fun User.toApiUserModel(): UserModel {
    return UserModel(userId = userId, userName = userName, password = password, salt = salt)
}