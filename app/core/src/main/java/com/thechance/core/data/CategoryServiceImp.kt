package com.thechance.core.data

import com.thechance.api.model.Category
import com.thechance.api.model.Market
import com.thechance.api.service.CategoryService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.toLowerCase
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.MarketTable
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent

class CategoryServiceImp(private val database: CoreDataBase) : BaseService(database, CategoriesTable), CategoryService,
    KoinComponent {

    override suspend fun create(categoryName: String, marketId: Long): Category = dbQuery {
        val categoryList = getCategoriesByMarketId(marketId)

        if (categoryList.isEmpty()) {
            val newCategory = CategoriesTable.insert {
                it[name] = categoryName
                it[isDeleted] = false
                it[this.marketId] = marketId
            }
            Category(
                categoryId = newCategory[CategoriesTable.id].value,
                categoryName = newCategory[CategoriesTable.name].toString(),
            )
        } else {
            throw InvalidInputException("This category with name $categoryName already exist.")
        }
    }

    override suspend fun getCategoriesByMarketId(marketId: Long): List<Category> {
        return dbQuery {
            CategoriesTable.select {
                CategoriesTable.marketId eq marketId and
                        CategoriesTable.isDeleted.eq(false)
            }.map { resultRow ->
                Category(
                    categoryId = resultRow[CategoriesTable.id].value,
                    categoryName = resultRow[CategoriesTable.name].toString(),
                )
            }
        }
    }


    override suspend fun delete(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select {
            CategoriesTable.id eq categoryId and Op.build { CategoriesTable.isDeleted eq false }
        }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                it[isDeleted] = true
            } > 0
        } else {
            throw IdNotFoundException("This category id $categoryId not found.")
        }
    }

    override suspend fun update(categoryId: Long, categoryName: String): Category = dbQuery {
        CategoriesTable.update({ CategoriesTable.id eq categoryId }) {
            it[name] = categoryName
        }
        val updatedCategory = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        if (updatedCategory != null) {
            Category(
                categoryId = updatedCategory[CategoriesTable.id].value,
                categoryName = updatedCategory[CategoriesTable.name]
            )
        } else {
            throw IdNotFoundException("Category with ID $categoryId not found.")
        }
    }


    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw IdNotFoundException("Category with ID $marketId not found.")
    }
}