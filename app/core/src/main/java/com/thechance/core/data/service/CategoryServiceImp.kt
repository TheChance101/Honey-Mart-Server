package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.CategoryWithProduct
import com.thechance.api.model.Product
import com.thechance.api.service.CategoryService
import com.thechance.api.utils.Error
import com.thechance.api.utils.ErrorType
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent
import java.util.*
import kotlin.NoSuchElementException

class CategoryServiceImp: BaseService(CategoriesTable), CategoryService,
    KoinComponent {

    override suspend fun create(categoryName: String, marketId: Long): Category = dbQuery {
        val categoryList =
            getCategoriesByMarketId(marketId).filter {
                it.categoryName.lowercase(Locale.getDefault()) == categoryName.lowercase(
                    Locale.getDefault()
                )
            }

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
            throw NoSuchElementException("This category with name $categoryName already exist.")
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
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    override suspend fun update(categoryId: Long, categoryName: String): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
                if (categoryName.isNotEmpty()) {
                    categoryRow[name] = categoryName
                }
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    override suspend fun isDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Category with ID $marketId not found.")
    }


    private suspend fun isCategoryDeleted(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        } ?: throw NoSuchElementException("Category with ID $categoryId not found.")
    }


    override suspend fun getProductsFromCategory(categoryId: Long?): CategoryWithProduct {
        if (categoryId != null && isCategoryDeleted(categoryId)) {
            val categoryProducts = dbQuery {
                (ProductTable innerJoin CategoryProductTable)
                    .select { CategoryProductTable.categoryId eq categoryId }
                    .map { productRow ->
                        Product(
                            id = productRow[ProductTable.id].value,
                            name = productRow[ProductTable.name].toString(),
                            price = productRow[ProductTable.price],
                            quantity = productRow[ProductTable.quantity],
                        )
                    }
            }
            val categoryName = dbQuery {
                CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()?.get(CategoriesTable.name)
                    ?: ""
            }

            return CategoryWithProduct(
                categoryId = categoryId,
                categoryName = categoryName,
                products = categoryProducts
            )
        } else {
            throw Error(ErrorType.DELETED_ITEM)
        }
    }
}