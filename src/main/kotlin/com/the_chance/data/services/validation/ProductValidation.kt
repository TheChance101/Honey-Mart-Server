package com.the_chance.data.services.validation

class ProductValidation {

    fun checkCreateValidation(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>?
    ): List<String> {
        val error = mutableListOf<String>()

        if (!checkNameLength(productName)) {
            error.add("The product name should have a length greater than 6 and shorter than 20 characters .")
        }

        if (!checkPrice(productPrice)) {
            error.add("The product Price should be in range 0.1 to 1000.000 .")
        }

        if (productQuantity != null && !checkNameLength(productQuantity)) {
            error.add("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
        }

        if (categoriesId == null || categoriesId.filterNot { it == 0L }.isEmpty()) {
            error.add("The product must be assigned to at least one category.")
        }

        return error
    }

    fun checkUpdateValidation(
        productName: String?, productPrice: Double?, productQuantity: String?
    ): List<String> {
        val error = mutableListOf<String>()

        if (productName != null && !checkNameLength(productName)) {
            error.add("The product name should have a length greater than 6 and shorter than 20 characters .")
        }

        if (productPrice != null && !checkPrice(productPrice)) {
            error.add("The product Price should be in range 0.1 to 1000.000 .")
        }

        if (productQuantity != null && !checkNameLength(productQuantity)) {
            error.add("The product Quantity should have a length greater than 6 and shorter than 20 characters .")
        }

        return error
    }

    private fun checkNameLength(productName: String): Boolean {
        return productName.length in 6..20
    }

    private fun checkPrice(price: Double): Boolean {
        return price in 0.1..999999.0
    }

    fun checkId(id: Long?): Boolean {
        return id != null
    }
}