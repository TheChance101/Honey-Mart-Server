package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.models.MarketWithCategories
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

interface MarketCategoriesService {

    suspend fun getAllMarketsWithCategories(): List<MarketWithCategories>

    suspend fun getCategoriesByMarket(marketId: Long): List<Category>

    suspend fun isDeleted(marketId: Long): Boolean

}