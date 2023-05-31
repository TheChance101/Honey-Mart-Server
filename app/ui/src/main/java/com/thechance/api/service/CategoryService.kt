package com.thechance.api.service


import com.thechance.api.model.Category

interface CategoryService {

    suspend fun create(categoryName: String, marketId: Long): Category

    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>

    suspend fun delete(categoryId: Long , marketId: Long): Boolean

    suspend fun update(categoryId: Long, categoryName: String): Category

    suspend fun isMarketDeleted(marketId: Long): Boolean
    suspend fun isCategoryDeleted(categoryId: Long): Boolean

}