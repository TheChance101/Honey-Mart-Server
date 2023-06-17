package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Product
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>?,
        marketOwnerId: Long?,
        role: String?,
        images: List<Long>?
    ): Boolean {
        isValidInput(productName, productPrice, productQuantity, categoriesId, marketOwnerId, role)?.let { throw it }

        return if (repository.checkCategoriesInDb(categoriesId!!)) {
            if (isMarketOwner(marketOwnerId!!, categoryId = categoriesId[0])) {
                repository.createProduct(productName, productPrice, productQuantity!!, categoriesId, images!!)
            } else {
                throw UnauthorizedException()
            }
        } else {
            throw NotValidCategoryList()
        }
    }

    private fun isValidInput(
        productName: String,
        productPrice: Double,
        productQuantity: String?,
        categoriesId: List<Long>?,
        marketOwnerId: Long?,
        role: String?
    ): Exception? {
        return when {
            !isValidProductName(productName) -> {
                InvalidProductNameException()
            }

            checkProductQuantity(productQuantity) -> {
                InvalidProductQuantityException()
            }

            isInvalidPrice(productPrice) -> {
                InvalidProductPriceException()
            }

            isValidIds(categoriesId) -> {
                InvalidCategoryIdException()
            }

            isInvalidId(marketOwnerId) -> {
                InvalidOwnerIdException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            else -> {
                null
            }
        }
    }

    private fun isValidProductName(productName: String?): Boolean {
        return if (productName == null) {
            false
        } else {
            val fullNameRegex = Regex("^[a-zA-Z0-9 ]{4,20}$")
            fullNameRegex.matches(productName)
        }
    }

    private fun checkProductQuantity(quantity: String?): Boolean {
        return quantity?.let {
            return it.length !in 6..20
        } ?: true
    }

    private suspend fun isMarketOwner(marketOwnerId: Long, categoryId: Long): Boolean {
        val marketId = repository.getMarketIdByCategoryId(categoryId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }
}