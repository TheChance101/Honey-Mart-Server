package com.thechance.core.data.repository.dataSource

import com.thechance.core.entity.*

interface ProductDataSource {

    suspend fun createProduct(
        productName: String, productPrice: Double, productQuantity: String, categoriesId: List<Long>
    ): Product

    suspend fun getAllProducts(): List<Product>

    suspend fun getProduct(productId: Long): Product

    suspend fun getAllCategoryForProduct(productId: Long): List<Category>

    suspend fun updateProduct(
        productId: Long, productName: String?, productPrice: Double?, productQuantity: String?
    ): Boolean

    suspend fun updateProductCategory(productId: Long, categoryIds: List<Long>): Boolean

    suspend fun deleteProduct(productId: Long): Boolean

    suspend fun isDeleted(id: Long): Boolean?

    suspend fun checkCategoriesInDb(categoryIds: List<Long>): Boolean

    suspend fun getProductMarketId(productId: Long): Long

    suspend fun addImageToGallery(imagesUrl: List<String>, productId: Long): Boolean

    suspend fun getAllProductsInCategory(categoryId: Long): List<Product>

    suspend fun deleteImageFromProduct(productId: Long, imageId: Long): String
}