package com.thechance.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(val categoryId: Long, val categoryName: String, val imageId: Int)
