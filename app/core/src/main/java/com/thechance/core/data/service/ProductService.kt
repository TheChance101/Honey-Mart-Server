package com.thechance.core.data.service

import com.thechance.core.data.datasource.ProductDataSource
import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import com.thechance.core.data.validation.product.ProductValidation
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent


class ProductService(
    private val productValidation: ProductValidation,
    private val productDataSource: ProductDataSource
) :
    BaseService(ProductTable, CategoryProductTable), KoinComponent {

    suspend fun create(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>?
    ): Product {
        productValidation.checkCreateValidation(
            productName = productName, productPrice = productPrice, productQuantity = productQuantity,
            categoriesId = categoriesId
        )?.let { throw it }

        return if(productDataSource.checkCategoriesInDb(categoriesId!!))  {
            dbQuery {
                val newProduct = ProductTable.insert { productRow ->
                    productRow[name] = productName
                    productRow[price] = productPrice
                    productRow[quantity] = productQuantity
                }

                CategoryProductTable.batchInsert(categoriesId) { categoryId ->
                    this[CategoryProductTable.productId] = newProduct[ProductTable.id]
                    this[CategoryProductTable.categoryId] = categoryId
                }

                Product(
                    id = newProduct[ProductTable.id].value,
                    name = newProduct[ProductTable.name],
                    price = newProduct[ProductTable.price],
                    quantity = newProduct[ProductTable.quantity],
                )
            }
        } else {
            throw Exception()
        }
    }

    suspend fun getAllCategoryForProduct(productId: Long?): List<Category> {
        productValidation.checkId(productId)?.let { throw InvalidInputException() }
        return if (!isDeleted(productId!!)) {
            productDataSource.getAllCategoryForProduct(productId)
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean {
        productValidation.checkId(productId)?.let { throw InvalidInputException() }

        return if (!isDeleted(productId!!)) {
            productValidation.checkUpdateValidation(
                productName = productName, productPrice = productPrice, productQuantity = productQuantity
            )?.let { throw it }

            productDataSource.updateProduct(
                productId = productId, productName = productName, productPrice = productPrice,
                productQuantity = productQuantity
            )
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun updateProductCategory(productId: Long?, categoryIds: List<Long>): Boolean {
        productValidation.checkUpdateProductCategories(productId, categoryIds)?.let { throw it }
        return if (!isDeleted(productId!!)) {
            if (productDataSource.checkCategoriesInDb(categoryIds)) {
                dbQuery {
                    CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

                    CategoryProductTable.batchInsert(categoryIds) { categoryId ->
                        this[CategoryProductTable.productId] = productId
                        this[CategoryProductTable.categoryId] = categoryId
                    }
                    true
                }
            } else {
                throw InvalidInputException()
            }
        } else {
            throw IdNotFoundException()
        }
    }

    suspend fun deleteProduct(productId: Long?): Boolean {
        productValidation.checkId(productId)?.let { throw InvalidInputException() }
        return if (!isDeleted(productId!!)) {
            productDataSource.deleteProduct(productId)
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    private suspend fun isDeleted(id: Long): Boolean =
        productDataSource.isDeleted(id)

    /**
     * validate that each categoryId is found and not deleted
     * */

}