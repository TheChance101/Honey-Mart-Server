package com.thechance.core.data.service

import com.thechance.core.data.datasource.CategoryDataSource
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

class CategoryService(
    private val categoryValidation: CategoryValidation,
    private val categoryDataSource: CategoryDataSource
) : BaseService(CategoriesTable,CategoryProductTable),
    KoinComponent {

    suspend fun create(categoryName: String?, marketId: Long?, imageId: Int?): Category {
        categoryValidation.checkCreateValidation(
            categoryName = categoryName, marketId = marketId, imageId = imageId
        )?.let { throw it }

        return if (!isMarketDeleted(marketId!!)) {
            if (categoryDataSource.isCategoryNameUnique(categoryName!!)) {
                categoryDataSource.createCategory(
                    categoryName = categoryName, marketId = marketId, imageId = imageId!!
                )
            } else {
                throw InvalidInputException()
            }
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun getCategoriesByMarketId(marketId: Long?): List<Category> {
        categoryValidation.checkId(marketId)?.let { throw InvalidInputException() }
        return if (!isMarketDeleted(marketId!!)) {
            categoryDataSource.getCategoriesByMarketId(marketId)
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun delete(categoryId: Long?): Boolean {
        categoryValidation.checkId(categoryId)?.let { throw InvalidInputException() }

        return if (!isCategoryDeleted(categoryId!!)) {
            categoryDataSource.deleteCategory(categoryId)
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun update(
        categoryId: Long?,
        categoryName: String?,
        marketId: Long?,
        imageId: Int?): Boolean {
        categoryValidation.checkUpdateValidation(
            categoryId = categoryId, categoryName = categoryName, marketId = marketId, imageId = imageId
        )?.let { throw it }

        return if (isMarketDeleted(marketId!!)) {
            if (!isCategoryDeleted(categoryId!!)) {
                categoryDataSource.updateCategory(
                    categoryId = categoryId, categoryName = categoryName, marketId = marketId, imageId = imageId
                )
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
            categoryDataSource.getAllProductsInCategory(categoryId)
        } else {
            throw ItemNotAvailableException()
        }
    }

    private suspend fun isMarketDeleted(marketId: Long): Boolean {
       return categoryDataSource.isMarketDeleted(marketId)?:throw IdNotFoundException()
    }
    private suspend fun isCategoryDeleted(categoryId: Long): Boolean {
       return categoryDataSource.isCategoryDeleted(categoryId)?:throw IdNotFoundException()
    }

}