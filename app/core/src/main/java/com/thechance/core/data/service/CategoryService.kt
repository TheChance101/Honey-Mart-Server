package com.thechance.core.data.service

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import com.thechance.core.data.validation.category.CategoryValidation
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent
import java.util.*

class CategoryService(private val categoryValidation: CategoryValidation) : BaseService(CategoriesTable),
    KoinComponent {

    suspend fun create(categoryName: String?, marketId: Long?, imageId: Int?): Category {
        categoryValidation.checkCreateValidation(
            categoryName = categoryName, marketId = marketId, imageId = imageId
        )?.let { throw it }

        return if (!isMarketDeleted(marketId!!)) {
            if (isCategoryNameUnique(categoryName!!)) {
                dbQuery {
                    val newCategory = CategoriesTable.insert {
                        it[name] = categoryName
                        it[isDeleted] = false
                        it[this.marketId] = marketId
                        it[this.imageId] = imageId!!
                    }
                    Category(
                        categoryId = newCategory[CategoriesTable.id].value,
                        categoryName = newCategory[CategoriesTable.name].toString(),
                        imageId = newCategory[CategoriesTable.imageId]
                    )
                }
            } else {
                throw InvalidInputException()
            }
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun delete(categoryId: Long?): Boolean {
        categoryValidation.checkId(categoryId)?.let { throw InvalidInputException() }

        return if (!isCategoryDeleted(categoryId!!)) {
            dbQuery {
                CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                    it[isDeleted] = true
                }
            }
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun update(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): Boolean {
        categoryValidation.checkUpdateValidation(
            categoryId = categoryId, categoryName = categoryName, marketId = marketId, imageId = imageId
        )?.let { throw it }

        return if (isMarketDeleted(marketId!!)) {
            if (!isCategoryDeleted(categoryId!!)) {
                dbQuery {
                    CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->

                        categoryName?.let { categoryRow[name] = it }

                        imageId?.let {
                            categoryRow[CategoriesTable.imageId] = it
                        }
                    }
                    true
                }

            } else {
                throw ItemNotAvailableException()
            }
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun getAllProductsInCategory(categoryId: Long?): List<Product> {
        categoryValidation.checkId(categoryId)?.let { throw InvalidInputException() }

        return if (!isCategoryDeleted(categoryId!!)) {
            dbQuery {
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
        } else {
            throw ItemNotAvailableException()
        }
    }

    private suspend fun isCategoryDeleted(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        } ?: throw IdNotFoundException()
    }

    private suspend fun isMarketDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException()
    }

    /**
     * TODO: need check per Market.
     * */
    private suspend fun isCategoryNameUnique(categoryName: String): Boolean {
        val category = dbQuery {
            CategoriesTable.select {
                CategoriesTable.name.lowerCase() eq categoryName.lowercase() and
                        CategoriesTable.isDeleted.eq(false)
            }.singleOrNull()
        }
        return category == null
    }
}