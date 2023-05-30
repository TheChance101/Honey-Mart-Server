package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import com.the_chance.utils.toLowerCase
import org.jetbrains.exposed.sql.*

interface CategoryService {

    suspend fun create(categoryName: String, marketId: Long): Category

    suspend fun getCategoriesByMarketId(marketId: Long): List<Category>

    suspend fun delete(categoryId: Long): Boolean

    suspend fun update(categoryId: Long, categoryName: String): Boolean

    suspend fun isDeleted(marketId: Long): Boolean
}