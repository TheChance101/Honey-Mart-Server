package com.thechance.core.data.validation.product

interface ProductValidation {
    fun checkCreateValidation(
        productName: String, productPrice: Double, productQuantity: String?, categoriesId: List<Long>?
    ): List<String>

    fun checkUpdateValidation(productName: String?, productPrice: Double?, productQuantity: String?): List<String>

    fun checkId(id: Long?): Boolean
}