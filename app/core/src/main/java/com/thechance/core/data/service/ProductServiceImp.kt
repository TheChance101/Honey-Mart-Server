package com.thechance.core.data.service

import com.thechance.api.model.Category
import com.thechance.api.model.Product
import com.thechance.api.model.ProductWithCategory
import com.thechance.api.service.ProductService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException
import com.thechance.core.data.mapper.toCategory
import com.thechance.core.data.mapper.toProductWithCategory
import com.thechance.core.data.tables.CategoriesTable
import com.thechance.core.data.tables.CategoryProductTable
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.validation.product.ProductValidation
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.koin.core.component.KoinComponent


class ProductServiceImp(private val productValidation: ProductValidation) :
    BaseService(ProductTable, CategoryProductTable), ProductService, KoinComponent {

    override suspend fun create(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): ProductWithCategory {
        productValidation.checkCreateValidation(
            productName = productName, productPrice = productPrice, productQuantity = productQuantity,
            categoriesId = categoriesId
        )?.let { throw it }

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

                ProductWithCategory(
                    id = newProduct[ProductTable.id].value,
                    name = newProduct[ProductTable.name],
                    price = newProduct[ProductTable.price],
                    quantity = newProduct[ProductTable.quantity],
                    category = (CategoriesTable innerJoin CategoryProductTable)
                        .select { CategoryProductTable.productId eq newProduct[ProductTable.id].value }
                        .map { categoryRow ->
                            Category(
                                categoryId = categoryRow[CategoriesTable.id].value,
                                categoryName = categoryRow[CategoriesTable.name].toString(),
                                imageId = categoryRow[CategoriesTable.imageId]
                            )
                        }
                )
            }
        } else {
            throw Exception("not valid categories.")
        }
    }

    override suspend fun getAllProducts(): List<Product> {
        return dbQuery {
            ProductTable.select { ProductTable.isDeleted eq false }.map { productRow ->
                Product(
                    id = productRow[ProductTable.id].value,
                    name = productRow[ProductTable.name].toString(),
                    price = productRow[ProductTable.price],
                    quantity = productRow[ProductTable.quantity],
                )
            }
        }
    }

    override suspend fun getAllCategoryForProduct(productId: Long?): List<Category> {
        productValidation.checkId(productId)?.let {
            throw InvalidInputException(it)
        }

        return if (!isDeleted(productId!!)) {
            dbQuery {
                (CategoriesTable innerJoin CategoryProductTable)
                    .select { CategoryProductTable.productId eq productId }
                    .map { it.toCategory() }
            }
        } else {
            throw ItemNotAvailableException("The item is no longer available.")
        }
    }

    override suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String {
        productValidation.checkId(productId)?.let {
            throw InvalidInputException(it)
        }

        return if (!isDeleted(productId!!)) {
            productValidation.checkUpdateValidation(
                productName = productName, productPrice = productPrice, productQuantity = productQuantity
            )?.let { throw it }

            dbQuery {
                ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                    productName?.let { productRow[name] = it }
                    productPrice?.let { productRow[price] = it }
                    productQuantity?.let { productRow[quantity] = it }
                }
            }
            "Product Updated successfully."
        } else {
            throw ItemNotAvailableException("This product is no longer available.")
        }
    }

    override suspend fun updateProductCategory(productId: Long?, categoryIds: List<Long>): ProductWithCategory {
        productValidation.checkUpdateProductCategories(productId, categoryIds)?.let { throw it }
        return if (!isDeleted(productId!!)) {
            if (checkCategoriesInDb(categoryIds)) {
                dbQuery {
                    CategoryProductTable.deleteWhere { CategoryProductTable.productId eq productId }

                    CategoryProductTable.batchInsert(categoryIds) { categoryId ->
                        this[CategoryProductTable.productId] = productId
                        this[CategoryProductTable.categoryId] = categoryId
                    }

                    val product = ProductTable.select { ProductTable.id eq productId }.map {
                        val categories = (CategoriesTable innerJoin CategoryProductTable)
                            .select { CategoryProductTable.productId eq it[ProductTable.id].value }
                            .map { it.toCategory() }
                        it.toProductWithCategory(categories)
                    }.single()
                    product
                }
            } else {
                throw InvalidInputException(message = "Error in categoryIds")
            }
        } else {
            throw IdNotFoundException(message = "Product with id $productId already deleted!")
        }
    }

    override suspend fun deleteProduct(productId: Long?): Boolean {
        productValidation.checkId(productId)?.let {
            throw InvalidInputException(message = it)
        }
        return if (!isDeleted(productId!!)) {
            dbQuery {
                ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                    productRow[isDeleted] = true
                }
            }
            true
        } else {
            throw ItemNotAvailableException(message = "This product is no longer available.")
        }
    }

    private suspend fun isDeleted(id: Long): Boolean {
        val product = dbQuery {
            ProductTable.select { ProductTable.id eq id }.singleOrNull()
                ?: throw ItemNotAvailableException(message = "The item with ID $id was not found.")
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