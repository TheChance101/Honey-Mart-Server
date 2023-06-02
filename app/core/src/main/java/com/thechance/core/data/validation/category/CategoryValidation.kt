package com.thechance.core.data.validation.category

interface CategoryValidation {
    fun checkCreateValidation(categoryName: String, marketId: Long?, imageId: Int?): Exception?
    fun checkCategoryId(categoryId: Long?): String?
}