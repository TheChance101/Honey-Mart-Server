package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.product.Product
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class CreateProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        productName: String, productPrice: Double, description: String?, categoriesId: List<Long>?,
        marketOwnerId: Long?, role: String?
    ): Product {
        isValidInput(productName, productPrice, description, categoriesId, marketOwnerId, role)?.let { throw it }
        return if (repository.checkCategoriesInDb(categoriesId!!)) {
            if (isMarketOwner(marketOwnerId!!, categoryId = categoriesId[0])) {
                val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId)
                repository.createProduct(productName, productPrice, description!!, categoriesId, marketId!!)
            } else {
                throw UnauthorizedException()
            }
        } else {
            throw NotValidCategoryList()
        }
    }

    private fun isValidInput(
        productName: String, productPrice: Double, description: String?, categoriesId: List<Long>?,
        marketOwnerId: Long?, role: String?
    ): Exception? {
        return when {
            !isValidMarketProductName(productName) -> {
                InvalidProductNameException()
            }

            isInValidDescription(description) -> {
                InvalidProductDescriptionException()
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

    private suspend fun isMarketOwner(marketOwnerId: Long, categoryId: Long): Boolean {
        val marketId = repository.getMarketIdByCategoryId(categoryId)
        return repository.getOwnerIdByMarketId(marketId) == marketOwnerId
    }
}