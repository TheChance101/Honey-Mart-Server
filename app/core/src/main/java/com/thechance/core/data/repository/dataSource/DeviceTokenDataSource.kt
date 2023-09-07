package com.thechance.core.data.repository.dataSource

interface DeviceTokenDataSource {
    suspend fun getUserDeviceTokens(userId: Long): List<String>
    suspend fun getOwnerDeviceTokens(ownerId: Long): List<String>
    suspend fun saveUserDeviceTokens(userId: Long, token: String): Boolean
    suspend fun saveOwnerDeviceTokens(ownerId: Long, token: String): Boolean
}