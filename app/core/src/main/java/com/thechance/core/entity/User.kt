package com.thechance.core.entity

data class User(val userId: Long, val userName: String, val password: String, val salt: String)
