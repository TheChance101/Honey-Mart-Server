package com.thechance.core.data.datasource

import com.thechance.core.data.model.UserAuthRequest
import com.thechance.core.data.security.hashing.HashingService
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.data.tables.UserTable
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class UserDataSourceImp : UserDataSource, KoinComponent {
    override suspend fun createUser(userName: String, password: String, saltedHash: SaltedHash): Boolean {
        return dbQuery {
            val newUser = UserTable.insert {
                it[UserTable.userName] = userName
                it[UserTable.password] = saltedHash.hash
                it[UserTable.salt] = saltedHash.salt
//                it[UserTable.isDeleted] = false
            }
            UserAuthRequest(
                userId = newUser[UserTable.id].value,
                userName = newUser[UserTable.userName],
                password = newUser[UserTable.password],
                salt = newUser[UserTable.salt]
            )
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

    override suspend fun getUserByName(name: String): UserAuthRequest? {
        return dbQuery {
            UserTable.select(UserTable.userName eq name).map {
                UserAuthRequest(
                    userId = it[UserTable.id].value,
                    userName = it[UserTable.userName],
                    password = it[UserTable.password],
                    salt = it[UserTable.salt]
                )
            }.singleOrNull()

        }
    }


}