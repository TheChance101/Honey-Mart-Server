package com.thechance.core.data.security.hashing

data class SaltedHash(
    val hash: String,
    val salt: String
)
