package com.example.ui.service

import com.example.model.Category
import com.example.model.MarketWithCategories

interface MarketCategoriesService {

    suspend fun getAllMarketsWithCategories(): List<MarketWithCategories>

    suspend fun getCategoriesByMarket(marketId: Long): List<Category>

    suspend fun isDeleted(marketId: Long): Boolean

}