package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.CategoryWithProduct
import com.thechance.api.model.Product
import com.thechance.api.service.CategoryService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.MarketTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.validation.category.CategoryValidation
import org.jetbrains.exposed.sql.*
import org.koin.core.component.KoinComponent
import java.util.*

class CategoryServiceImp(private val categoryValidation: CategoryValidation) : BaseService(CategoriesTable),
    CategoryService, KoinComponent {

    override suspend fun create(categoryName: String?, marketId: Long?, imageId: Int?): Category {
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
                throw NoSuchElementException("This category with name $categoryName already exist.")
            }
        } else {
            throw IdNotFoundException("There is no market with id $marketId ")
        }
    }

    override suspend fun getCategoriesByMarketId(marketId: Long?): List<Category> {
        categoryValidation.checkId(marketId)?.let { throw InvalidInputException(it) }
        return if (!isMarketDeleted(marketId!!)) {
            dbQuery {
                CategoriesTable.select {
                    CategoriesTable.marketId eq marketId and
                            CategoriesTable.isDeleted.eq(false)
                }.map { resultRow ->
                    Category(
                        categoryId = resultRow[CategoriesTable.id].value,
                        categoryName = resultRow[CategoriesTable.name].toString(),
                        imageId = resultRow[CategoriesTable.imageId]
                    )
                }
            }
        } else {
            throw IdNotFoundException("There is no market with id $marketId ")
        }
    }

    override suspend fun delete(categoryId: Long?): String {
        categoryValidation.checkId(categoryId)?.let {
            throw InvalidInputException(it)
        }

        return if (!isCategoryDeleted(categoryId!!)) {
            dbQuery {
                CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                    it[isDeleted] = true
                }
            }
            "Category Deleted successfully."
        } else {
            throw ItemNotAvailableException("The item is no longer available.")
        }
    }

    override suspend fun update(categoryId: Long?, categoryName: String?, marketId: Long?, imageId: Int?): String {
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
                    "Product Updated successfully."
                }

            } else {
                throw ItemNotAvailableException("This category is no longer available.")
            }
        } else {
            throw ItemNotAvailableException("This market with id $marketId is no longer available.")
        }
    }

    override suspend fun getAllProductsInCategory(categoryId: Long?): CategoryWithProduct {
        categoryValidation.checkId(categoryId)?.let { throw InvalidInputException(it) }

        if (!isCategoryDeleted(categoryId!!)) {
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
            val resultRow = dbQuery {
                CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
            }
            val category = resultRow?.let {
                Category(
                    categoryId = resultRow[CategoriesTable.id].value,
                    categoryName = resultRow[CategoriesTable.name].toString(),
                    imageId = resultRow[CategoriesTable.imageId]
                )
            } ?: throw NoSuchElementException("Category with ID $categoryId not found.")

            return CategoryWithProduct(
                categoryId = category.categoryId,
                categoryName = category.categoryName,
                categoryImageId = category.imageId,
                products = categoryProducts
            )
        } else {
            throw ItemNotAvailableException("Category with id $categoryId already deleted.")
        }
    }

    private suspend fun isCategoryDeleted(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        category?.let {
            it[CategoriesTable.isDeleted]
        } ?: throw NoSuchElementException("Category with ID $categoryId not found.")
    }

    private suspend fun isMarketDeleted(marketId: Long): Boolean = dbQuery {
        val market = MarketTable.select { MarketTable.id eq marketId }.singleOrNull()
        market?.let {
            it[MarketTable.isDeleted]
        } ?: throw NoSuchElementException("Market with ID $marketId not found.")
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