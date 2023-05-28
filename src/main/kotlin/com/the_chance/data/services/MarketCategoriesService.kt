package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.models.MarketWithCategories
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select

class MarketCategoriesService(database: Database) : BaseService(database, MarketTable, CategoriesTable) {

    suspend fun getAllMarketsWithCategories(): List<MarketWithCategories> = dbQuery {
        val markets = MarketTable
            .select {
                MarketTable.isDeleted eq false
            }
            .map {
                val marketId = it[MarketTable.id].value
                val marketName = it[MarketTable.name]
                MarketWithCategories(
                    marketId = marketId,
                    marketName = marketName,
                    categories = getCategoriesByMarket(marketId)
                )
            }

        markets
    }

     suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable
            .select {
                (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
            }
            .map {
                Category(
                    categoryId = it[CategoriesTable.id].value,
                    categoryName = it[CategoriesTable.name]
                )
            }
    }

}