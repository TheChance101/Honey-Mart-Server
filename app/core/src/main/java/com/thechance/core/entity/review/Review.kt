package com.thechance.core.entity.review

import com.thechance.core.entity.User
import java.time.LocalDateTime

data class Review(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: LocalDateTime,
    val user: User
)
