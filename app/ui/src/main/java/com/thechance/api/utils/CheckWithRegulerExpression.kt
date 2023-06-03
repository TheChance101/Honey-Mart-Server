package com.thechance.api.utils

import java.util.regex.Pattern

fun isValidStringInput(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z\\s]+$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}