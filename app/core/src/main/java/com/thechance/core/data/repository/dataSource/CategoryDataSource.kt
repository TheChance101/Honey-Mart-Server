package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*

interface CategoryDataSource {

    suspend fun createCategory(categoryName: String, marketId: Long, imageId: Int): Boolean
    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>
    suspend fun deleteCategory(categoryId: Long): Boolean
    suspend fun updateCategory(categoryId: Long, categoryName: String?, marketId: Long, imageId: Int?): Boolean
    suspend fun isCategoryDeleted(categoryId: Long): Boolean?
    suspend fun isCategoryNameUnique(categoryName: String, marketId: Long): Boolean
    suspend fun getMarketIdByCategoryId(categoryId: Long): Long

}