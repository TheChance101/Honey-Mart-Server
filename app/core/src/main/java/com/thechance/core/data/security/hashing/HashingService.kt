package com.thechance.core.data.security.hashing

interface HashingService {
    fun generateSaltedHash(value: String, saltLength: Int = 32): String
    fun verify(value: String, saltedHash: SaltedHash): Boolean
}