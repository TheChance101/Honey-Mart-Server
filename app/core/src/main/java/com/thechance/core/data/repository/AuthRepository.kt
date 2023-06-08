package com.thechance.core.data.repository

import com.thechance.core.data.model.Owner

interface AuthRepository {
    //region user

    // Create User
    suspend fun createUser(userName: String, password: String): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    // Login user
    suspend fun validateUser(name:String, password: String):String

    //endregion


    //region owner
    suspend fun createOwner(userName: String, password: String): Owner
    suspend fun isOwnerNameExists(ownerName: String): Boolean

    //endregion
}