package com.thechance.core.utils

import java.util.regex.Pattern

internal fun isValidUsername(username: String?): Boolean {
    return if (username == null) {
        false
    } else {
        val usernameRegex = Regex("^[a-zA-Z0-9_.]{6,20}$")
        usernameRegex.matches(username)
    }
}

internal fun isValidPassword(password: String?): Boolean {
    return if (password == null) {
        false
    } else {
        val passwordRegex = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,14}$")
        passwordRegex.matches(password)
    }
}

internal fun isValidString(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}

internal fun isValidNameLength(name: String): Boolean {
    return name.length in 4..14
}

internal fun isInvalidId(id: Long?): Boolean {
    return id == null || id == 0L
}

internal fun isValidIds(ids: List<Long>?): Boolean {
    return ids.isNullOrEmpty() || ids.filterNot { it == 0L }.isEmpty()
}

internal fun isInvalidPrice(price: Double?): Boolean {
    return price?.let {
        return it !in 0.1..999999.0
    } ?: true
}

internal fun isValidEmail(email: String?): Boolean {
    return if (email == null) {
        return false
    } else {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        emailRegex.matches(email)
    }
}

internal fun isValidFullName(fullName: String?): Boolean {
    return if (fullName == null) {
        return false
    } else {
        val fullNameRegex = Regex("^[a-zA-Z]{4,}\\s[a-zA-Z]{4,}$")
        fullNameRegex.matches(fullName)
    }
}

internal fun isValidRole(role: String, inputRole: String?): Boolean {
    return if (inputRole == null) {
        false
    } else {
        inputRole == role
    }
}