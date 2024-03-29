package com.thechance.core.data.datasource

import com.thechance.core.data.datasource.database.tables.MarketTable
import com.thechance.core.data.datasource.database.tables.category.CategoriesTable
import com.thechance.core.data.datasource.database.tables.category.CategoryProductTable
import com.thechance.core.data.datasource.database.tables.product.ProductTable
import com.thechance.core.data.datasource.mapper.toCategory
import com.thechance.core.data.datasource.mapper.toMarket
import com.thechance.core.data.repository.dataSource.MarketDataSource
import com.thechance.core.entity.Category
import com.thechance.core.entity.market.Market
import com.thechance.core.utils.PAGE_SIZE
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

    override suspend fun createMarket(ownerId: Long, name: String, address: String, description: String): Long? {
        dbQuery {
            MarketTable.insert {
                it[MarketTable.name] = name
                it[MarketTable.address] = address
                it[MarketTable.description] = description
                it[MarketTable.isDeleted] = false
                it[MarketTable.isApproved] = false
                it[MarketTable.ownerId] = ownerId
            }
        }
        return getMarketIdByOwnerId(ownerId)
    }

    override suspend fun addMarketImage(marketId: Long, imageUrl: String): Boolean {
        return dbQuery {
            MarketTable.update({ MarketTable.id eq marketId }) {
                it[MarketTable.imageUrl] = imageUrl
            }
            true
        }
    }

    override suspend fun getProductsCountForMarket(marketId: Long): Int = dbQuery {
        ProductTable.select { ProductTable.marketId eq marketId }
            .count().toInt()
    }

    override suspend fun getAllMarkets(page: Int): List<Market> = dbQuery {
        val offset = ((page - 1) * PAGE_SIZE).toLong()
        MarketTable.select { (MarketTable.isDeleted eq false) and (MarketTable.status eq true) and (MarketTable.isApproved eq true) }
            .limit(PAGE_SIZE, offset)
            .map { it.toMarket() }
            .toList()
    }

    override suspend fun getCategoriesByMarket(marketId: Long): List<Category> = dbQuery {
        CategoriesTable.select {
            (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
        }.map { it.toCategory() }
    }

    override suspend fun deleteMarket(marketId: Long): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { it[isDeleted] = true }
        deleteMarketCategories(marketId)
        true
    }

    override suspend fun deleteMarketCategories(marketId: Long): Boolean = dbQuery {
        //Get a list of category IDs associated with the marketId
        val categoryIdsToDelete = CategoriesTable
            .select {
                (CategoriesTable.marketId eq marketId) and
                        (CategoriesTable.isDeleted eq false)
            }.map { it[CategoriesTable.id].value }

        if (categoryIdsToDelete.isNotEmpty()) {
            //Mark categories as deleted
            CategoriesTable.update({ CategoriesTable.id inList categoryIdsToDelete }) {
                it[CategoriesTable.isDeleted] = true
            }

            //Mark associated products as deleted
            (ProductTable innerJoin CategoryProductTable).update(
                { CategoryProductTable.categoryId inList categoryIdsToDelete }
            ) {
                it[ProductTable.isDeleted] = true
            }
            true
        } else {
            //No categories found for the given marketId, nothing to delete
            false
        }
    }

    override suspend fun restoreMarket(marketId: Long): Boolean = dbQuery {
        //Restore the market
        val marketRestored = MarketTable.update({ MarketTable.id eq marketId }) {
            it[isDeleted] = false
        } > 0

        if (marketRestored) {
            //Restore associated categories
            CategoriesTable.update({ CategoriesTable.marketId eq marketId }) {
                it[CategoriesTable.isDeleted] = false
            }

            //Restore associated products
            ProductTable.update(
                { ProductTable.marketId eq marketId }
            ) {
                it[ProductTable.isDeleted] = false
            }

            true
        } else {
            //Market not found or already restored
            false
        }
    }

    override suspend fun updateMarket(
        marketId: Long, marketName: String?, imageUrl: String?, latitude: Double?, longitude: Double?,
        description: String?, address: String?
    ): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { marketRow ->
            marketName?.let { marketRow[MarketTable.name] = it }
            imageUrl?.let { marketRow[MarketTable.imageUrl] = it }
            latitude?.let { marketRow[MarketTable.latitude] = it }
            longitude?.let { marketRow[MarketTable.longitude] = it }
            description?.let { marketRow[MarketTable.description] = it }
            address?.let { marketRow[MarketTable.address] = it }
        }
        true
    }

    override suspend fun updateMarketStatus(marketId: Long, status: Boolean): Boolean = dbQuery {
        MarketTable.update({ MarketTable.id eq marketId }) { marketRow ->
            marketRow[MarketTable.status] = status
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
            ProductTable
                .select { ProductTable.id eq productId }
                .map { it[ProductTable.marketId] }
                .firstOrNull()?.value
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