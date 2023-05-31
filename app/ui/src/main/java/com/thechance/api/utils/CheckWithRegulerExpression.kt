package com.thechance.api.utils

import java.util.regex.Pattern

fun isValidInput(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}
