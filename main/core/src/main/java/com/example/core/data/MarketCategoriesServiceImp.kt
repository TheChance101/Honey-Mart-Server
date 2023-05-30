package com.example.core.data

import com.example.model.Category
import com.example.model.MarketWithCategories
import com.example.ui.service.MarketCategoriesService
import com.example.core.data.tables.CategoriesTable
import com.example.core.data.tables.MarketTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class MarketCategoriesServiceImp(database: Database) : BaseService(database, MarketTable, CategoriesTable),
    MarketCategoriesService,
    KoinComponent {

    override suspend fun getAllMarketsWithCategories(): List<MarketWithCategories> = dbQuery {
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

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> {
        return if (!isDeleted(marketId)) {
            dbQuery {
                CategoriesTable.select {
                    (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
                }.map {
                    Category(
                        categoryId = it[CategoriesTable.id].value,
                        categoryName = it[CategoriesTable.name]
                    )
                }
            }
        } else {
            throw NoSuchElementException("Market with ID $marketId not found.")
        }
    }

    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Market with ID $marketId not found.")
    }

}