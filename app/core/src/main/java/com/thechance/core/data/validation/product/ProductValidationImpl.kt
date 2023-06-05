package com.thechance.core.data.validation.product

import com.thechance.core.data.utils.*
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
            InvalidInputException()
        }
    }

    override fun checkUpdateValidation(
        productName: String?, productPrice: Double?, productQuantity: String?
    ): Exception? {
        val exception = mutableListOf<String>()

        if (productName.isNullOrEmpty() && productPrice == null && productQuantity.isNullOrEmpty()) {
            throw ProductUpdateException()
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
            InvalidInputException()
        }
    }

    override fun checkId(id: Long?): String? {
        return if (id == null) {
            throw IdNotFoundException()
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
            InvalidInputException()
        }
    }

    private fun checkProductName(productName: String?): String? {
        return productName?.let {
            return if (it.length !in 6..20) {
                throw ProductNameLengthException()
            } else {
                null
            }
        } ?: throw ProductEmptyNameException()
    }

    private fun checkProductQuantity(quantity: String?): String? {
        return quantity?.let {
            return if (it.length !in 1..15) {
                throw ProductQuantityLengthException()
            } else {
                null
            }
        } ?: throw ProductEmptyQuantityException()
    }

    private fun checkPrice(price: Double?): String? {
        return price?.let {
            return if (it !in 0.1..999999.0) {
                throw ProductPriceRangeException()
            } else {
                null
            }
        } ?: throw ProductEmptyPriceException()

    }

    private fun isValidCategoryIds(categoryIds: List<Long>?): String? {
        return if (categoryIds.isNullOrEmpty() || categoryIds.filterNot { it == 0L }.isEmpty()) {
            throw ProductAssignCategoryException()
        }else
        {
            null
        }
    }
}