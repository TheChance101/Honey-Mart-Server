package com.example.ui.service


import com.example.model.Category

interface CategoryService {

    suspend fun create(categoryName: String, marketId: Long): Category

    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>

    suspend fun delete(categoryId: Long): Boolean

    suspend fun update(categoryId: Long, categoryName: String): Boolean

    suspend fun isDeleted(marketId: Long): Boolean
}