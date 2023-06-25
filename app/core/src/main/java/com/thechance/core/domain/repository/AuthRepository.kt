package com.thechance.core.domain.repository

import com.thechance.core.data.security.token.Tokens
import com.thechance.core.entity.*

interface AuthRepository {

    //region user

    suspend fun createUser(password: String, fullName: String, email: String): Boolean

    suspend fun isEmailExists(email: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    suspend fun getUserByEmail(email: String): User

    fun isUserValidPassword(user: User, password: String): Boolean

    suspend fun getProfile(userId: Long): User

    //endregion


    //region owner
    suspend fun createOwner(fullName: String, email: String, password: String): Boolean

    suspend fun isOwnerEmailExists(email: String): Boolean

    suspend fun getMarketOwnerByUsername(userName: String): Owner

    fun isOwnerValidPassword(owner: Owner, password: String): Boolean

    suspend fun isValidOwner(ownerId: Long): Boolean

    suspend fun getOwner(ownerId: Long): User
    //endregion


    fun getTokens(id: Long, role: String): Tokens
}