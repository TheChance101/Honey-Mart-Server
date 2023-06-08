package com.thechance.core.data.datasource

import com.thechance.core.data.model.User
import com.thechance.core.data.security.hashing.SaltedHash

interface UserDataSource {

    suspend fun createUser(userName: String, saltedHash: SaltedHash): Boolean
    suspend fun isUserNameExists(userName: String): Boolean

    suspend fun getUserByName(userName:String):User


}