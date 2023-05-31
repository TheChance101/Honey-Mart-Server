package com.thechance.api.service


import com.thechance.api.model.Category

interface CategoryService {

    suspend fun create(categoryName: String, marketId: Long): Category

    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>

    suspend fun delete(categoryId: Long): Boolean

    suspend fun update(categoryId: Long, categoryName: String): Category

    suspend fun isDeleted(marketId: Long): Boolean
}