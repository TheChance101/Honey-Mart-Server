package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.notification.OwnerDeviceTokenTable
import com.thechance.core.data.datasource.database.tables.notification.UserDeviceTokenTable
import com.thechance.core.data.repository.dataSource.DeviceTokenDataSource
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class DeviceTokenDataSourceImp : DeviceTokenDataSource, KoinComponent {

    override suspend fun getDeviceTokens(receiverId: Long): List<String> {
        return dbQuery {
            UserDeviceTokenTable.select { UserDeviceTokenTable.userId eq receiverId }.map {
                it[UserDeviceTokenTable.token]
            }.toList()
        }
    }

    override suspend fun saveUserDeviceTokens(userId: Long, token: String): Boolean =
        dbQuery {
            UserDeviceTokenTable.deleteWhere { UserDeviceTokenTable.token eq token }
            UserDeviceTokenTable.insert {
                it[this.token] = token
                it[this.userId] = userId
            }
            true
        }

    override suspend fun saveOwnerDeviceTokens(ownerId: Long, token: String): Boolean =
        dbQuery {
            OwnerDeviceTokenTable.deleteWhere { OwnerDeviceTokenTable.token eq token }
            OwnerDeviceTokenTable.insert {
                it[this.token] = token
                it[this.ownerId] = ownerId
            }
            true
        }
}