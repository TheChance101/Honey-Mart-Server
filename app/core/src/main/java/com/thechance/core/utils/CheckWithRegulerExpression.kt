package com.thechance.core.utils

import java.util.regex.Pattern

fun isValidStringInput(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z]+\$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}

fun isValidMarketName(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}