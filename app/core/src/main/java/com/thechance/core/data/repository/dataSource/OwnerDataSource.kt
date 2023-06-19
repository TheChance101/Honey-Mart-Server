package com.thechance.core.data.repository.dataSource

import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.entity.*

interface OwnerDataSource {

    suspend fun createOwner(fullName: String, email: String, password: String, saltedHash: SaltedHash): Boolean

    suspend fun isOwnerEmailExists(email: String): Boolean

    suspend fun getOwnerByEmail(email: String): Owner

    suspend fun isValidOwner(ownerId: Long): Boolean

    suspend fun getOwner(ownerId: Long): User
}