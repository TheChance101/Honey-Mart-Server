package com.thechance.api.service

import com.thechance.api.model.Category
import com.thechance.api.model.MarketWithCategories

interface MarketCategoriesService {

    suspend fun getAllMarketsWithCategories(): List<MarketWithCategories>

    suspend fun getCategoriesByMarket(marketId: Long): List<Category>

    suspend fun isDeleted(marketId: Long): Boolean

}