package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.entity.Owner
import com.thechance.core.data.datasource.database.tables.OwnerTable
import com.thechance.core.data.repository.dataSource.OwnerDataSource
import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.entity.User
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class OwnerDataSourceImp : OwnerDataSource, KoinComponent {

    override suspend fun createOwner(
        fullName: String, email: String, password: String, saltedHash: SaltedHash
    ): Boolean {
        return dbQuery {
            OwnerTable.insert {
                it[OwnerTable.fullName] = fullName
                it[OwnerTable.email] = email
                it[NormalUserTable.password] = saltedHash.hash
                it[NormalUserTable.salt] = saltedHash.salt
                it[isDeleted] = false
            }
            true
        }
    }

    override suspend fun isOwnerEmailExists(email: String): Boolean {
        return dbQuery { OwnerTable.select { OwnerTable.email eq email }.count() > 0 }
    }

    override suspend fun getOwnerByEmail(email: String): Owner {
        return dbQuery {
            OwnerTable.select { OwnerTable.email eq email }.map {
                Owner(
                    ownerId = it[OwnerTable.id].value,
                    email = it[OwnerTable.email],
                    fullName = it[OwnerTable.fullName],
                    password = it[OwnerTable.password],
                    salt = it[OwnerTable.salt]
                )
            }.single()
        }
    }

    override suspend fun isValidOwner(ownerId: Long): Boolean {
        return dbQuery {
            val owner = OwnerTable.select { OwnerTable.id eq ownerId }.singleOrNull()
            if (owner != null) {
                val ownerHasMarket = MarketTable.select { MarketTable.ownerId eq ownerId }.singleOrNull()
                ownerHasMarket == null
            } else {
                false
            }
        }
    }

    override suspend fun getOwner(ownerId: Long): User {
        return dbQuery {
            val owner = OwnerTable.select { OwnerTable.id eq ownerId }.single()
            User(
                userId = owner[OwnerTable.id].value,
                email = owner[OwnerTable.email],
                fullName = owner[OwnerTable.fullName],
                password = "",
                salt = "",
                profileImage = ""
            )
        }
    }
}