package com.thechance.api.utils

fun Double?.orZero() = this ?: 0.0


fun String?.toLongIds() = this?.split(",")?.map { it.toLongOrNull() ?: 0L } ?: emptyList()
