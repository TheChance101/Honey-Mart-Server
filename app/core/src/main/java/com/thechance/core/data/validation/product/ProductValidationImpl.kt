package com.thechance.core.data.validation.product

import com.thechance.api.utils.InvalidInputException
import org.koin.core.component.KoinComponent

class ProductValidationImpl : ProductValidation, KoinComponent {

    override fun checkCreateValidation(
        productName: String,
        productPrice: Double,
        productQuantity: String?
    ): Exception? {
        return if (!checkNameLength(productName)) {
            InvalidInputException("The product name should have a length greater than 6 and shorter than 20 characters .")
        } else if (!checkPrice(productPrice)) {
            InvalidInputException("The product Price should be in range 0.1 to 1000.000 .")
        } else if (productQuantity != null && !checkNameLength(productQuantity)) {
            InvalidInputException("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
        } else {
            null
        }
    }

    override fun checkUpdateValidation(
        productName: String?, productPrice: Double?, productQuantity: String?
    ): Exception? {
        return if (productName != null && !checkNameLength(productName)) {
            InvalidInputException("The product name should have a length greater than 6 and shorter than 20 characters .")
        } else if (productPrice != null && !checkPrice(productPrice)) {
            InvalidInputException("The product Price should be in range 0.1 to 1000.000 .")
        } else if (productQuantity != null && !checkNameLength(productQuantity)) {
            InvalidInputException("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
        } else {
            null
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