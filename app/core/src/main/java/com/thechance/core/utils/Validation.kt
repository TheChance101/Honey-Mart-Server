package com.thechance.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun isValidMarketProductName(name: String?): Boolean {
    return if (name == null) {
        false
    } else {
        val pattern = Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]{4,20}$")
        pattern.matches(name)
    }
}

fun isValidQuery(name: String?): Boolean {
    return if (name == null) {
        false
    } else {
        val pattern = Regex("^[A-Za-z0-9\\s\\[\\]\\(\\)\\-.,&]*$")
        pattern.matches(name)
    }
}

fun isValidCategoryName(name: String?): Boolean {
    return if (name == null) {
        false
    } else {
        val pattern = Regex("^[a-zA-Z]{4,16}$")
        pattern.matches(name)
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

internal fun isInvalidId(id: Long?): Boolean {
    return id == null || id == 0L
}

internal fun isInvalidNumber(number: Int?): Boolean {
    return number == null || number <= 0
}

internal fun isInvalidPageNumber(page: Int?): Boolean {
    return page == null || page < 1
}

internal fun isValidIds(ids: List<Long>?): Boolean {
    return ids.isNullOrEmpty() || ids.filterNot { it == 0L }.isEmpty()
}

internal fun isInvalidPrice(price: Double?): Boolean {
    return price?.let {
        return it !in 0.1..999999.0
    } ?: true
}

internal fun isInvalidPercentage(percentage: Double?): Boolean {
    return percentage?.let {
        it < 0.0 || it > 100.0
    } ?: true
}

internal fun isInvalidDate(date: String?): Boolean {
    return date?.let {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val parsedDate = LocalDateTime.parse(it, formatter)
            parsedDate.isBefore(LocalDateTime.now())
        } catch (e: Exception) {
            true
        }
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

internal fun isInValidDescription(description: String?): Boolean {
    return description?.let {
        return it.length !in 6..500
    } ?: true
}

internal fun isValidAddress(address: String?): Boolean {
    return if (address.isNullOrEmpty()) {
        false
    } else {
        val pattern = Regex("^[0-9]+\\s[A-Za-z\\s]+,[A-Za-z\\s]+,[A-Za-z]{2}$")
        pattern.matches(address.trim())
    }
}