package com.thechance.api.utils

import java.time.LocalDateTime

fun LocalDateTime.convertDateToMillis(): Long {
    return this.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
}