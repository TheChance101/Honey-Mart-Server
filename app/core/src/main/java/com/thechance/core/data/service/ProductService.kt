package com.thechance.core.data.service

import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.utils.IdNotFoundException
import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.ItemNotAvailableException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent


class ProductService : BaseService(ProductTable, CategoryProductTable), KoinComponent {

    suspend fun create(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): Product {

        return if (checkCategoriesInDb(categoriesId!!)) {
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
        return if (!isDeleted(productId!!)) {
            dbQuery {
                (CategoriesTable innerJoin CategoryProductTable)
                    .select { CategoryProductTable.productId eq productId }
                    .map { it.toCategory() }
            }
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean {
        return if (!isDeleted(productId!!)) {
            dbQuery {
                ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                    productName?.let { productRow[name] = it }
                    productPrice?.let { productRow[price] = it }
                    productQuantity?.let { productRow[quantity] = it }
                }
            }
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    suspend fun updateProductCategory(productId: Long?, categoryIds: List<Long>): Boolean {
        return if (!isDeleted(productId!!)) {
            if (checkCategoriesInDb(categoryIds)) {
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
        return if (!isDeleted(productId!!)) {
            dbQuery {
                ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                    productRow[isDeleted] = true
                }
            }
            true
        } else {
            throw ItemNotAvailableException()
        }
    }

    private suspend fun isDeleted(id: Long): Boolean {
        val product = dbQuery {
            ProductTable.select { ProductTable.id eq id }.singleOrNull()
                ?: throw ItemNotAvailableException()
        }
        return product[ProductTable.isDeleted]
    }

    /**
     * validate that each categoryId is found and not deleted
     * */
    private suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean {
        return dbQuery {
            CategoriesTable.select { CategoriesTable.id inList categoryIds }
                .filterNot { it[CategoriesTable.isDeleted] }.toList().size == categoryIds.size
        }
    }

}