package com.thechance.api.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryModel(val categoryId: Long, val categoryName: String, val imageId: Int)
