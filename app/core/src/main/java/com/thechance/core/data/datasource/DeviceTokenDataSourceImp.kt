package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.notification.DeviceTokenTable
import com.thechance.core.data.repository.dataSource.DeviceTokenDataSource
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class DeviceTokenDataSourceImp : DeviceTokenDataSource, KoinComponent {

    override suspend fun getDeviceTokens(receiverId: Long): List<String> {
        return dbQuery {
            DeviceTokenTable.select { DeviceTokenTable.receiverId eq receiverId }.map {
                it[DeviceTokenTable.token]
            }.toList()
        }
    }

    override suspend fun saveDeviceTokens(receiverId: Long,token: String): Boolean =
        dbQuery {
            DeviceTokenTable.insert {
                it[this.token] = token
                it[this.receiverId] = receiverId
            }
            true
        }
}