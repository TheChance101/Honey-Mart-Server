package com.thechance.api.service


import com.thechance.api.model.Category
import com.thechance.api.model.CategoryWithProduct

interface CategoryService {

    suspend fun create(categoryName: String, marketId: Long,imageId: Int): Category

    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>

    suspend fun delete(categoryId: Long?): String

    suspend fun update(categoryId: Long, categoryName: String): Boolean

    suspend fun isDeleted(marketId: Long): Boolean

    suspend fun getProductsFromCategory(categoryId: Long?): CategoryWithProduct

}