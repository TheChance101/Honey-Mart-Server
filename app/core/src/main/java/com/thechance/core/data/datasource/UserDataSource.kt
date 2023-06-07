package com.thechance.core.data.datasource

import com.thechance.core.data.model.User

interface UserDataSource {

    suspend fun createUser(userName: String, password: String): User
    suspend fun isUserNameExists(userName: String): Boolean


}