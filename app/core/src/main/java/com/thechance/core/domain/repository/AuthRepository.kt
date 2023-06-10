package com.thechance.core.domain.repository

import com.thechance.core.entity.*

interface AuthRepository {

    //region user

    suspend fun createUser(userName: String, password: String, fullName: String, email: String): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun isEmailExists(email: String): Boolean

    suspend fun isUserExist(userId: Long): Boolean

    //endregion


    //region owner
    suspend fun createOwner(userName: String, password: String): Owner
    suspend fun isOwnerNameExists(ownerName: String): Boolean

    //endregion
    suspend fun getUserByName(userName: String): User
    fun isValidPassword(user: User, password: String): Boolean

    fun getToken(user: User): String
}