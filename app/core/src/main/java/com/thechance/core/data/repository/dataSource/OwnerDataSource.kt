package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*

interface OwnerDataSource {

    suspend fun createOwner(ownerName: String, password: String): Owner
    suspend fun isOwnerNameExists(ownerName: String): Boolean


}