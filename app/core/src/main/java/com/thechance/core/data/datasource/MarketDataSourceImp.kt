package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toMarket
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.repository.dataSource.MarketDataSource
import com.thechance.core.entity.Category
import com.thechance.core.entity.market.Market
import com.thechance.core.utils.dbQuery
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketDataSourceImp : MarketDataSource, KoinComponent {

    override suspend fun getMarketIdByOwnerId(ownerId: Long): Long? {
        return dbQuery {
            MarketTable.select { MarketTable.ownerId eq ownerId }.map {
                it[MarketTable.id].value
            }.singleOrNull()
        }
    }

    override suspend fun getOwnerIdByMarketId(marketId: Long): Long {
        return dbQuery {
            MarketTable.select { MarketTable.id eq marketId }.map {
                it[MarketTable.ownerId].value
            }.single()
        }
    }

    override suspend fun createMarket(marketName: String, ownerId: Long): Boolean =
        dbQuery {
            val newMarket = MarketTable.insert {
                it[name] = marketName
                it[isDeleted] = false
                it[MarketTable.ownerId] = ownerId
            }
            true
        }

    override suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean {
        return dbQuery {
            MarketTable.update({ MarketTable.id eq marketId }) {
                it[MarketTable.imageUrl] = imageUrl
            }
            true
        }
    }

    override suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map { it.toMarket() }
    }

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable.select {
            (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
        }.map { it.toCategory() }
    }

    override suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { it[isDeleted] = true }
        true
    }


    override suspend fun updateMarket(marketId: Long, marketName: String?, imageUrl: String?): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { marketRow ->
            marketName?.let { marketRow[MarketTable.name] = it }
            imageUrl?.let { marketRow[MarketTable.imageUrl] = it }
        }
        true
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

    override suspend fun getMarket(marketId: Long): Market? {
        return dbQuery {
            MarketTable.select { MarketTable.id eq marketId }.map {
                it.toMarket()
            }.singleOrNull()
        }
    }

}