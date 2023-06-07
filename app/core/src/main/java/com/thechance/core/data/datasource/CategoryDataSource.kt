package com.thechance.core.data.datasource

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product

interface CategoryDataSource {

    suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Category
    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>
    suspend fun deleteCategory(categoryId: Long): Boolean
    suspend fun updateCategory(categoryId: Long, categoryName: String?, marketId: Long, imageId: Int?): Boolean
    suspend fun getAllProductsInCategory(categoryId: Long): List<Product>
    suspend fun isCategoryDeleted(categoryId: Long): Boolean?
    suspend fun isCategoryNameUnique(categoryName: String): Boolean

}