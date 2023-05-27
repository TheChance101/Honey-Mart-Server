package com.the_chance.utils

import java.util.regex.Pattern

fun isValidStringInput(name: String): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z]+\$")
    val matcher = pattern.matcher(name)
    return matcher.matches()
}