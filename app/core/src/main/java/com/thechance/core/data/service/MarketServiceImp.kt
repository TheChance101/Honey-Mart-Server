package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.Market
import com.thechance.api.model.MarketWithCategories
import com.thechance.api.service.MarketService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.ItemNotAvailableException
import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.mapper.toMarket
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.validation.market.MarketValidation
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketServiceImp(private val marketValidationImpl: MarketValidation) : BaseService(MarketTable, CategoriesTable),
    MarketService, KoinComponent {

    override suspend fun createMarket(marketName: String?): Market {
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }

        return dbQuery {
            val newMarket = MarketTable.insert {
                it[name] = marketName!!
                it[isDeleted] = false
            }
            Market(marketId = newMarket[MarketTable.id].value, marketName = newMarket[MarketTable.name])
        }
    }

    override suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map { it.toMarket() }
    }

    override suspend fun getCategoriesByMarket(marketId: Long?): List<Category> {
        marketValidationImpl.checkId(marketId)?.let { throw it }

        return if (!isDeleted(marketId!!)) {
            dbQuery {
                CategoriesTable.select {
                    (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
                }.map { it.toCategory() }
            }
        } else {
            throw NoSuchElementException("Market with ID $marketId not found.")
        }
    }

    override suspend fun getAllMarketsWithCategories(): List<MarketWithCategories> = dbQuery {
        val markets = MarketTable
            .select {
                MarketTable.isDeleted eq false
            }.map {
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

    override suspend fun deleteMarket(marketId: Long?): Boolean = dbQuery {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        if (isDeleted(marketId!!)) {
            throw ItemNotAvailableException("Market with ID $marketId already deleted")
        } else {
            MarketTable.update({ MarketTable.id eq marketId }) {
                it[isDeleted] = true
            }
            true
        }
    }

    override suspend fun updateMarket(marketId: Long, marketName: String?): Market {
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }
        return dbQuery {
            if (isDeleted(marketId)) {
                throw ItemNotAvailableException("Market with ID: $marketId has been deleted")
            } else {
                MarketTable.update({ MarketTable.id eq marketId }) {
                    it[name] = marketName!!
                }
                Market(marketId = marketId, marketName = marketName!!)
            }
        }
    }

    private suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw IdNotFoundException("Market with ID $marketId not found.")
    }

}