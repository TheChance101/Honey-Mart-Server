package com.thechance.core.data.datasource

import com.thechance.core.data.repository.dataSource.AdminDataSource
import com.thechance.core.entity.Admin
import org.koin.core.component.KoinComponent

class AdminDataSourceImp : AdminDataSource, KoinComponent {

    override suspend fun getAdminByEmail(email: String): Admin {
        return Admin(
            adminId = 1,
            email = System.getenv("adminEmail"),
            fullName = System.getenv("adminFullName"),
            password = System.getenv("adminPassword"),
            salt = ""
        )
    }

    override suspend fun isValidAdmin(password: String, email: String): Boolean {
        return getAdminByEmail(email).let {
            it.password == password && it.email == email
        }
    }

}