package com.thechance.core.data.validation.product

interface ProductValidation {
    fun checkCreateValidation(productName: String, productPrice: Double, productQuantity: String?): Exception?
    fun checkUpdateValidation(productName: String?, productPrice: Double?, productQuantity: String?): Exception?
    fun checkId(id: Long?): Boolean
}