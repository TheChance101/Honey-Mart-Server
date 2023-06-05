package com.thechance.core.data.validation.category

interface CategoryValidation {

    fun checkUpdateValidation(
        categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?
    ): Exception?

    fun checkId(categoryId: Long?): String?
}