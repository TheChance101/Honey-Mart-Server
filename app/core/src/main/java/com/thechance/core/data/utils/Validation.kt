package com.thechance.core.data.utils

import org.mindrot.jbcrypt.BCrypt
import java.util.regex.Pattern

internal fun checkName(name: String?): Boolean {
    return if (name == null || !isValidNameLength(name)) {
        true
    } else {
        !isValidString(name)
    }
}

internal fun checkPassword(password: String?): Boolean {
    return if (password == null || !isValidPasswordLength(password)) {
        true
    } else {
        !isValidString(password)
    }
}
internal fun encryptPassword(password: String): String {
    return BCrypt.hashpw(password, BCrypt.gensalt())
}


internal fun isValidString(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}

internal fun isValidNameLength(name: String): Boolean {
    return name.length in 4..14
}

internal fun isValidPasswordLength(name: String): Boolean {
    return name.length in 6..14
}

internal fun isInvalidId(id: Long?): Boolean {
    return id == null || id == 0L
}

internal fun isValidIds(ids: List<Long>?): Boolean {
    return ids.isNullOrEmpty() || ids.filterNot { it == 0L }.isEmpty()
}

internal fun InvalidPrice(price: Double?): Boolean {
    return price?.let {
        return it !in 0.1..999999.0
    } ?: true
}