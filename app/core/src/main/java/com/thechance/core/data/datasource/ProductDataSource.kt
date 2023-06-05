package com.thechance.core.data.datasource

import com.thechance.core.data.model.Category
import com.thechance.core.data.model.Product
import com.thechance.core.data.model.ProductWithCategory

interface ProductDataSource {

    suspend fun createProduct(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>
    ): ProductWithCategory

    suspend fun getAllProducts(): List<Product>
    suspend fun getAllCategoryForProduct(productId: Long?): List<Category>
    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): Int

    suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): ProductWithCategory
    suspend fun deleteProduct(productId: Long?): Int
    suspend fun isDeleted(id: Long): Boolean
    suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean
}