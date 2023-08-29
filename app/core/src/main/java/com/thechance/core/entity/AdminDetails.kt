package com.thechance.core.entity

data class AdminDetails(val adminId: Long, val email: String, val fullName: String, val password: String, var salt: String)
