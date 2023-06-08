package com.thechance.core.data.datasource

import com.thechance.core.data.model.UserAuthRequest
import com.thechance.core.data.security.hashing.SaltedHash

interface UserDataSource {

    suspend fun createUser(userName: String, password: String  , saltedHash: SaltedHash): Boolean
    suspend fun isUserNameExists(userName: String): Boolean


}