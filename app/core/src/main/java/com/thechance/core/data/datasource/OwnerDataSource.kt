package com.thechance.core.data.datasource

import com.thechance.core.data.model.Owner

interface OwnerDataSource {

    suspend fun createOwner(ownerName: String, password: String): Owner
    suspend fun isOwnerNameExists(ownerName: String): Boolean


}