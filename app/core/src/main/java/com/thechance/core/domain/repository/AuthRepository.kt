package com.thechance.core.domain.repository

import com.thechance.core.data.security.hashing.SaltedHash
import com.thechance.core.entity.*

interface AuthRepository {

    //region user

    suspend fun createUser(userName: String, password: String, fullName: String, email: String): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun isEmailExists(email: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByName(userName: String): User

    fun isUserValidPassword(user: User, password: String): Boolean

    //endregion


    //region owner
    suspend fun createOwner(userName: String, password: String): Boolean
    suspend fun isOwnerNameExists(ownerName: String): Boolean

    suspend fun getMarketOwnerByUsername(userName: String): Owner

    fun isOwnerValidPassword(owner: Owner, password: String): Boolean

    //endregion


    fun getToken(id: Long, role: String): String
}