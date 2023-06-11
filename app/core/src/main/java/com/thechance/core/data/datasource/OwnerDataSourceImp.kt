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

    override suspend fun createOwner(ownerName: String, password: String, saltedHash: SaltedHash): Boolean {
        return dbQuery {
            OwnerTable.insert {
                it[OwnerTable.ownerName] = ownerName
                it[NormalUserTable.password] = saltedHash.hash
                it[NormalUserTable.salt] = saltedHash.salt
                it[isDeleted] = false
            }
            true
        }
    }

    override suspend fun isOwnerNameExists(ownerName: String): Boolean {
        return dbQuery {
            OwnerTable.select {
                OwnerTable.ownerName eq ownerName
            }.count() > 0
        }
    }

    override suspend fun getOwnerByUserName(userName: String): Owner {
        return dbQuery {
            OwnerTable.select { OwnerTable.ownerName eq userName }.map {
                Owner(
                    ownerId = it[OwnerTable.id].value,
                    userName = it[OwnerTable.ownerName],
                    password = it[OwnerTable.password],
                    salt = it[OwnerTable.salt]
                )
            }.single()
        }
    }

}