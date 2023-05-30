package com.the_chance.data.services

import com.the_chance.data.models.Product
import com.the_chance.data.services.validation.Error
import com.the_chance.data.services.validation.ErrorType
import com.the_chance.data.services.validation.ProductValidation
import com.the_chance.data.tables.ProductTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.InvalidPropertiesFormatException

interface ProductService {

    suspend fun create(productName: String, productPrice: Double, productQuantity: String?): Product

    suspend fun getAllProducts(): List<Product>

    suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String

    suspend fun deleteProduct(productId: Long?): String

}