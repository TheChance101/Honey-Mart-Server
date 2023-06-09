package com.thechance.core.data.datasource

import com.thechance.core.data.model.Owner
import com.thechance.core.data.database.tables.OwnerTable
import com.thechance.core.data.datasource.OwnerDataSource
import com.thechance.core.data.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class OwnerDataSourceImp : OwnerDataSource, KoinComponent {

    override suspend fun createOwner(ownerName: String, password: String): Owner {
        return dbQuery {
            val newOwner = OwnerTable.insert {
                it[OwnerTable.ownerName] = ownerName
                it[OwnerTable.password] = password
                it[OwnerTable.isDeleted] = false
            }
            Owner(
                ownerId = newOwner[OwnerTable.id].value,
                userName = newOwner[OwnerTable.ownerName],
                password = newOwner[OwnerTable.password],
            )
        }
    }

    override suspend fun isOwnerNameExists(ownerName: String): Boolean {
        return dbQuery {
            OwnerTable.select {
                OwnerTable.ownerName eq ownerName
            }.count() > 0
        }
    }


}