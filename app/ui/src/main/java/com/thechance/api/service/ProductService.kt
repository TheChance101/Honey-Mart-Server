package com.thechance.api.service

import com.thechance.api.model.Category
import com.thechance.api.model.Product
import com.thechance.api.model.ProductWithCategory

interface ProductService {

    suspend fun create(productName: String, productPrice: Double, productQuantity: String?,categoriesId: List<Long>?): ProductWithCategory

    suspend fun getAllProducts(): List<Product>

    suspend fun getAllCategoryForProduct(productId: Long?): List<Category>

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String

    suspend fun deleteProduct(productId: Long?): String

}