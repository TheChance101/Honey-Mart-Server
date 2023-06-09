package com.thechance.core.data.datasource

import com.thechance.core.data.model.User
import com.thechance.core.data.tables.UserTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {
    override suspend fun createUser(userName: String, password: String): User {
        return dbQuery {
            val newUser = UserTable.insert {
                it[UserTable.userName] = userName
                it[UserTable.password] = password
                it[UserTable.isDeleted] = false
            }
            User(
                userId = newUser[UserTable.id].value,
                userName = newUser[UserTable.userName],
            )
        }
    }

    override suspend fun isUserNameExists(userName: String): Boolean {
        return dbQuery {
            UserTable.select {
                UserTable.userName eq userName
            }.count() > 0
        }
    }



}