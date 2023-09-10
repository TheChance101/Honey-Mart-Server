package com.thechance.api.model.review

import com.thechance.api.model.UserModel
import kotlinx.serialization.Serializable

@Serializable
data class ReviewModel(
    val reviewId: Long,
    val content: String,
    val rating: Int,
    val reviewDate: Long,
    val user: UserModel
)