package com.thechance.api.service

import com.thechance.api.model.Category
import com.thechance.api.model.Market
import com.thechance.api.model.MarketWithCategories

interface MarketService {

    suspend fun createMarket(marketName: String?): Market

    suspend fun getAllMarkets(): List<Market>

    suspend fun getCategoriesByMarket(marketId: Long?): List<Category>

    suspend fun getAllMarketsWithCategories(): List<MarketWithCategories>

    suspend fun deleteMarket(marketId: Long?): Boolean

    suspend fun updateMarket(marketId: Long?, marketName: String?): Market

}