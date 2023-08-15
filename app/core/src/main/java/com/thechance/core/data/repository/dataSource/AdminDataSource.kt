package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.Admin

interface AdminDataSource {
    suspend fun getAdminByEmail(email: String): Admin
    suspend fun isValidAdmin( password: String, email: String): Boolean
}