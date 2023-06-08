package com.thechance.core.data.datasource

import com.thechance.core.data.model.User
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.tables.UserTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {
    override suspend fun createUser(userName: String, saltedHash: SaltedHash): Boolean {
        return dbQuery {
            UserTable.insert {
                it[UserTable.userName] = userName
                it[UserTable.password] = saltedHash.hash
                it[UserTable.salt] = saltedHash.salt
            }
            true
        }
    }

    override suspend fun isUserNameExists(userName: String): Boolean {
        return dbQuery {
            UserTable.select {
                UserTable.userName eq userName
            }.count() > 0
        }
    }

    override suspend fun getUserByName(name: String): User? {
        return dbQuery {
            UserTable.select(UserTable.userName eq name).map {
                User(
                    userId = it[UserTable.id].value,
                    userName = it[UserTable.userName],
                    password = it[UserTable.password],
                    salt = it[UserTable.salt]
                )
            }.singleOrNull()

        }
    }

}