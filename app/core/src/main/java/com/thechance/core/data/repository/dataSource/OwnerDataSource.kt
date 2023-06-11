package com.thechance.core.data.repository.dataSource

import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.entity.*

interface OwnerDataSource {

    suspend fun createOwner(ownerName: String, password: String, saltedHash: SaltedHash): Boolean
    suspend fun isOwnerNameExists(ownerName: String): Boolean
    suspend fun getOwnerByUserName(userName: String): Owner

}