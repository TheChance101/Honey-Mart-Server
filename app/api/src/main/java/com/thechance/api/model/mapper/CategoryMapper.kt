package com.thechance.api.model.mapper

import com.thechance.api.model.CategoryModel
import com.thechance.core.entity.Category


internal fun Category.toApiCategoryModel(): CategoryModel {
    return CategoryModel(
        categoryId = categoryId,
        categoryName = categoryName,
        imageId = imageId
    )
}