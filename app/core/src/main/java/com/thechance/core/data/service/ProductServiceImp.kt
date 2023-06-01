package com.thechance.core.data.service

import com.thechance.api.model.Product
import com.thechance.api.service.ProductService
import com.thechance.api.utils.IdNotFoundException
import com.thechance.api.utils.InvalidInputException
import com.thechance.api.utils.ItemNotAvailableException
import com.thechance.core.data.tables.ProductTable
import com.thechance.core.data.validation.product.ProductValidation
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.koin.core.component.KoinComponent

class ProductServiceImp(
    private val productValidationImpl: ProductValidation
) : BaseService(ProductTable), ProductService, KoinComponent {

    override suspend fun create(productName: String, productPrice: Double, productQuantity: String?): Product {
        val exception = productValidationImpl.checkCreateValidation(
            productName = productName,
            productPrice = productPrice,
            productQuantity = productQuantity
        )
        return if (exception == null) {
            dbQuery {
                val newProduct = ProductTable.insert { productRow ->
                    productRow[name] = productName
                    productRow[price] = productPrice
                    productRow[quantity] = productQuantity
                }
                Product(
                    id = newProduct[ProductTable.id].value,
                    name = newProduct[ProductTable.name],
                    price = newProduct[ProductTable.price],
                    quantity = newProduct[ProductTable.quantity],
                )
            }
        } else {
            throw exception
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

    override suspend fun updateProduct(
        productId: Long?, productName: String?, productPrice: Double?, productQuantity: String?
    ): String {
        if (productValidationImpl.checkId(productId)) {
            if (!isDeleted(productId!!)) {
                val exception = productValidationImpl.checkUpdateValidation(
                    productName = productName,
                    productPrice = productPrice,
                    productQuantity = productQuantity
                )
                return if (exception == null) {
                    dbQuery {
                        ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                            productName?.let { productRow[name] = it }
                            productPrice?.let { productRow[price] = it }
                            productQuantity?.let { productRow[quantity] = it }
                        }
                    }
                    "Product Updated successfully."
                } else {
                    throw exception
                }
            } else {
                throw ItemNotAvailableException("The item is no longer available.")
            }
        } else {
            throw IdNotFoundException("Id Not found ")
        }
    }

    override suspend fun deleteProduct(productId: Long?): String {
        return if (productValidationImpl.checkId(productId)) {
            if (!isDeleted(productId!!)) {
                dbQuery {
                    ProductTable.update({ ProductTable.id eq productId }) { productRow ->
                        productRow[isDeleted] = true
                    }
                }
                "Product Deleted successfully."
            } else {
                throw ItemNotAvailableException("The item is no longer available.")
            }
        } else {
            throw InvalidInputException("Invalid input")
        }
    }

    private suspend fun isDeleted(id: Long): Boolean = dbQuery {
        val product = ProductTable.select { ProductTable.id eq id }.singleOrNull()
        product?.let {
            it[ProductTable.isDeleted]
        } ?: throw ItemNotAvailableException("The item is no longer available.")
    }
}