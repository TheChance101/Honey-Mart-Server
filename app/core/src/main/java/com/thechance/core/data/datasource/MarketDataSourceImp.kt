package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toMarket
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.repository.dataSource.MarketDataSource
import com.thechance.core.entity.Category
import com.thechance.core.entity.Market
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketDataSourceImp : MarketDataSource, KoinComponent {
    override suspend fun createMarket(marketName: String): Market =
        dbQuery {
            val newMarket = MarketTable.insert {
                it[name] = marketName
                it[isDeleted] = false
            }
            Market(marketId = newMarket[MarketTable.id].value, marketName = newMarket[MarketTable.name])
        }

    override suspend fun getAllMarkets(): List<Market> = dbQuery {
        println("Ahmed")
        MarketTable.select { MarketTable.isDeleted eq false }.map { it.toMarket() }
    }

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable.select {
            (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
        }.map { it.toCategory() }
    }

    override suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[isDeleted] = true
        }
        true
    }


    override suspend fun updateMarket(marketId: Long, marketName: String): Market = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) {
            it[name] = marketName
        }
        Market(marketId = marketId, marketName = marketName)
    }

    override suspend fun isDeleted(marketId: Long): Boolean? = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        }
    }

    override suspend fun getMarketId(productId: Long): Long? {
        return dbQuery {
            val categoryId = CategoryProductTable.select { CategoryProductTable.productId eq productId }
                .map {
                    it[CategoryProductTable.categoryId].value
                }.firstOrNull()

            categoryId?.let {
                val marketId = CategoriesTable.select { CategoriesTable.id eq categoryId }
                    .map { category ->
                        category[CategoriesTable.marketId].value
                    }.singleOrNull()
                marketId
            }
        }
    }

}