package com.thechance.core.data.validation.product

import com.thechance.api.utils.InvalidInputException
import org.koin.core.component.KoinComponent

class ProductValidationImpl : ProductValidation, KoinComponent {

    override fun checkCreateValidation(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): Exception? {
        val exception = mutableListOf<String>()

        checkProductName(productName)?.let {
            exception.add(it)
        }

        checkProductQuantity(productQuantity)?.let {
            exception.add(it)
        }

        checkPrice(productPrice)?.let {
            exception.add(it)
        }

        isValidCategoryIds(categoriesId)?.let {
            exception.add(it)
        }

        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString(" ,\n "))
        }
    }

    override fun checkUpdateValidation(
        productName: String?, productPrice: Double?, productQuantity: String?
    ): Exception? {
        val exception = mutableListOf<String>()

        if (productName.isNullOrEmpty() && productPrice == null && productQuantity.isNullOrEmpty()) {
            exception.add("Can't do UPDATE without fields to update")
        } else if (!productName.isNullOrEmpty()) {
            checkProductName(productName)?.let {
                exception.add(it)
            }
        } else if (productQuantity != null) {
            checkProductQuantity(productQuantity)?.let {
                exception.add(it)
            }
        } else if (productPrice != null) {
            checkPrice(productPrice)?.let {
                exception.add(it)
            }
        }

        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString(" ,\n "))
        }
    }

    override fun checkId(id: Long?): String? {
        return if (id == null) {
            "Invalid product Id"
        } else {
            null
        }
    }

    override fun checkUpdateProductCategories(productId: Long?, categoryIds: List<Long>?): Exception? {
        val exceptions = mutableListOf<String>()

        checkId(productId)?.let {
            exceptions.add(it)
        }

        isValidCategoryIds(categoryIds)?.let {
            exceptions.add(it)
        }
        return if (exceptions.isEmpty()) {
            null
        } else {
            InvalidInputException(exceptions.toString())
        }
    }

    private fun checkProductName(productName: String?): String? {
        return productName?.let {
            return if (it.length !in 6..20) {
                "The product name should have a length greater than 6 and shorter than 20 characters ."
            } else {
                null
            }
        } ?: "The product name can not be empty"
    }

    private fun checkProductQuantity(quantity: String?): String? {
        return quantity?.let {
            return if (it.length !in 6..20) {
                "The product Quantity should have a length greater than 6 and shorter than 20 characters ."
            } else {
                null
            }
        } ?: "The product quantity can not be empty"
    }

    private fun checkPrice(price: Double?): String? {
        return price?.let {
            return if (it !in 0.1..999999.0) {
                "The product Price should be in range 0.1 to 1000.000 ."
            } else {
                null
            }
        } ?: "The product price can not be empty"

    }

    private fun isValidCategoryIds(categoryIds: List<Long>?): String? {
        return if (categoryIds.isNullOrEmpty() || categoryIds.filterNot { it == 0L }.isEmpty()) {
            "The product must be assigned to at least one category."
        } else {
            null
        }
    }

}