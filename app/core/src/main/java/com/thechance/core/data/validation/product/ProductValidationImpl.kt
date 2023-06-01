package com.thechance.core.data.validation.product

import com.thechance.api.utils.InvalidInputException
import org.koin.core.component.KoinComponent

class ProductValidationImpl : ProductValidation, KoinComponent {

    override fun checkCreateValidation(
        productName: String,
        productPrice: Double,
        productQuantity: String?
    ): Exception? {
        val exception = mutableListOf<String>()
        if (!checkNameLength(productName)) {
            exception.add("The product name should have a length greater than 6 and shorter than 20 characters .")
        }
        if (!checkPrice(productPrice)) {
            exception.add("The product Price should be in range 0.1 to 1000.000 .")
        }
        if (productQuantity != null && !checkNameLength(productQuantity)) {
            exception.add("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
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
        if (productName != null && !checkNameLength(productName)) {
            exception.add("The product name should have a length greater than 6 and shorter than 20 characters .")
        }
        if (productPrice != null && !checkPrice(productPrice)) {
            exception.add("The product Price should be in range 0.1 to 1000.000 .")
        }
        if (productQuantity != null && !checkNameLength(productQuantity)) {
            exception.add("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
        }
        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString(" ,\n "))
        }
    }

    override fun checkId(id: Long?): Boolean {
        return id != null
    }

    private fun checkNameLength(productName: String): Boolean {
        return productName.length in 6..20
    }

    private fun checkPrice(price: Double): Boolean {
        return price in 0.1..999999.0
    }
}