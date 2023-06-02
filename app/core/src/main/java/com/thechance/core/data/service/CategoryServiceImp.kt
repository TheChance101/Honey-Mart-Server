package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.CategoryWithProduct
import com.thechance.api.model.Product
import com.thechance.api.service.CategoryService
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

class CategoryServiceImp(
        private val categoryValidation: CategoryValidation
) : BaseService(CategoriesTable), CategoryService,
        KoinComponent {

    override suspend fun create(categoryName: String, marketId: Long, imageId: Int): Category {
        categoryValidation.checkCreateValidation(
                categoryName = categoryName, marketId = marketId, imageId = imageId
        )?.let { throw it }

        return if (isCategoryNameValidate(categoryName) == null) {
            dbQuery {
                val newCategory = CategoriesTable.insert {
                    it[name] = categoryName
                    it[isDeleted] = false
                    it[this.marketId] = marketId
                    it[this.imageId] = imageId
                }
                Category(
                        categoryId = newCategory[CategoriesTable.id].value,
                        categoryName = newCategory[CategoriesTable.name].toString(),
                        imageId = newCategory[CategoriesTable.imageId]
                )
            }
        } else {
            throw Exception("This category with name $categoryName already exist.")
        }
    }

    private suspend fun isCategoryNameValidate(categoryName: String): ResultRow? {
        return dbQuery {
            CategoriesTable.select {
                CategoriesTable.name.lowerCase() eq categoryName.lowercase() and
                        CategoriesTable.isDeleted.eq(false)
            }.singleOrNull()
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
                        imageId = resultRow[CategoriesTable.imageId]
                )
            }
        }
    }

    override suspend fun delete(categoryId: Long?): String {
        categoryValidation.checkCategoryId(categoryId)?.let {
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

    override suspend fun update(categoryId: Long?, categoryName: String): String {
        categoryValidation.checkCategoryId(categoryId)?.let {
            throw InvalidInputException(it)
        }

        if (!isCategoryDeleted(categoryId!!)) {
            val exception = categoryValidation.checkUpdateValidation(
                    categoryId = categoryId,
                    categoryName = categoryName
            )
            return if (exception == null) {
                dbQuery {
                    CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
                        if (categoryName.isNotEmpty()) {
                            categoryRow[name] = categoryName
                        }
                    }
                    "Product Updated successfully."
                }
            } else {
                throw exception
            }
        } else {
            throw ItemNotAvailableException("The item is no longer available.")
        }
    }

    /*
    * for what this function
     */
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
            throw Error()
        }
    }
}