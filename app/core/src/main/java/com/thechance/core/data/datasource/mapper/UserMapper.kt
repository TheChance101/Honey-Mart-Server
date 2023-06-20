package com.thechance.core.data.datasource.mapper

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.entity.User
import org.jetbrains.exposed.sql.ResultRow

internal fun ResultRow.toUser(): User {
    return User(
        userId = this[NormalUserTable.id].value,
        fullName = this[NormalUserTable.fullName],
        email = this[NormalUserTable.email],
        profileImage = this[NormalUserTable.imageUrl],
        password = this[NormalUserTable.password],
        salt = this[NormalUserTable.salt]
    )
}
