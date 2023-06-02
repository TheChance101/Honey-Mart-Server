package com.thechance.core.data.validation.category

interface CategoryValidation {
    fun checkCreateValidation(categoryName: String?, marketId: Long?, imageId: Int?): Exception?

    fun checkUpdateValidation(categoryId: Long?, categoryName: String?): Exception?
    fun checkCategoryId(categoryId: Long?): String?
}