package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.NormalUserTable
import com.thechance.core.entity.Owner
import com.thechance.core.data.datasource.database.tables.OwnerTable
import com.thechance.core.data.repository.dataSource.OwnerDataSource
import com.thechance.core.data.security.hashing.SaltedHash
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

}