package com.thechance.core.data.service

import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.mapper.toMarket
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Market
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.ItemAlreadyDeleted
import com.thechance.core.data.utils.ItemNotAvailableException
import com.thechance.core.data.validation.market.MarketValidation
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class MarketService(private val marketValidationImpl: MarketValidation) : BaseService(MarketTable, CategoriesTable),
    KoinComponent {

    suspend fun createMarket(marketName: String?): Market {
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }

        return dbQuery {
            val newMarket = MarketTable.insert {
                it[name] = marketName!!
                it[isDeleted] = false
            }
            Market(marketId = newMarket[MarketTable.id].value, marketName = newMarket[MarketTable.name])
        }
    }

    suspend fun getAllMarkets(): List<Market> = dbQuery {
        MarketTable.select { MarketTable.isDeleted eq false }.map { it.toMarket() }
    }

    suspend fun getCategoriesByMarket(marketId: Long?): List<Category> {
        marketValidationImpl.checkId(marketId)?.let { throw it }

        return if (!isDeleted(marketId!!)) {
            dbQuery {
                CategoriesTable.select {
                    (CategoriesTable.marketId eq marketId) and (CategoriesTable.isDeleted eq false)
                }.map { it.toCategory() }
            }
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun deleteMarket(marketId: Long?): Boolean = dbQuery {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        if (isDeleted(marketId!!)) {
            throw ItemNotAvailableException()
        } else {
            MarketTable.update({ MarketTable.id eq marketId }) {
                it[isDeleted] = true
            }
            true
        }
    }

    suspend fun updateMarket(marketId: Long?, marketName: String?): Market {
        marketValidationImpl.checkId(marketId)?.let { throw it }
        marketValidationImpl.checkMarketName(marketName)?.let { throw it }
        return if (isDeleted(marketId!!)) {
            throw ItemAlreadyDeleted()
        } else {
            dbQuery {
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
        } ?: throw IdNotFoundException()
    }

}