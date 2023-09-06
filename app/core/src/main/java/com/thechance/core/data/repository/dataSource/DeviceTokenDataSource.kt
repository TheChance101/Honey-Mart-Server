package com.thechance.core.data.repository.dataSource

interface DeviceTokenDataSource {
    suspend fun getDeviceTokens(receiverId: Long): List<String>
    suspend fun saveUserDeviceTokens(userId: Long, token: String): Boolean
    suspend fun saveOwnerDeviceTokens(ownerId: Long, token: String): Boolean
}