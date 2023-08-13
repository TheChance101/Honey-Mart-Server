package com.thechance.core.data.repository.dataSource

interface DeviceTokenDataSource {
    suspend fun getDeviceTokens(receiverId: Long): List<String>
    suspend fun saveDeviceTokens(receiverId: Long,token: String): Boolean
}