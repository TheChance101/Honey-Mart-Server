package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Long,
    val categoryName: String?,
    val imageId: Int?
)
