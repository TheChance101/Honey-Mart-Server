package com.thechance.core.data.utils

import java.util.regex.Pattern

internal fun checkName(name: String?): Boolean {
    return if (name == null || !isValidNameLength(name)) {
        true
    } else {
        !isValidString(name)
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

internal fun isValidId(id: Long?): Boolean {
    return id == null || id == 0L
}

internal fun isValidIds(ids: List<Long>?): Boolean {
    return ids.isNullOrEmpty() || ids.filterNot { it == 0L }.isEmpty()
}