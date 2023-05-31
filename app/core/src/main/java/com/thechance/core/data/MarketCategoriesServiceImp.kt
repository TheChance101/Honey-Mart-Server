package com.thechance.core.data

import com.thechance.api.model.Category
import com.thechance.api.model.MarketWithCategories
import com.thechance.api.service.MarketCategoriesService
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.koin.core.component.KoinComponent

class MarketCategoriesServiceImp(database: CoreDataBase) : BaseService(database, MarketTable, CategoriesTable),
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