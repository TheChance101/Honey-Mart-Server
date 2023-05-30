package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Long,
    val categoryName: String,
)
