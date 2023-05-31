package com.thechance.api.service

import com.thechance.api.model.Product

interface ProductService {

    suspend fun create(productName: String, productPrice: Double, productQuantity: String?): Product

    suspend fun getAllProducts(): List<Product>

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String

    suspend fun deleteProduct(productId: Long?): String

}