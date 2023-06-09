package com.thechance.core.data.usecase.wishlist

import com.thechance.core.data.model.WishList
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidProductIdException
import com.thechance.core.data.utils.InvalidUserIdException
import com.thechance.core.data.utils.NotValidCategoryList
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class CreateWishListUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(productId: Long?, userId: Long?): WishList {
        isValidInput(productId, userId)?.let { throw it }

        return if (repository.isProductInWishList(productId!!)) {
            repository.createWishList(productId, userId!!)
        } else {
            throw NotValidCategoryList()
        }
    }

    private fun isValidInput(
        productId: Long?, userId: Long?
    ): Exception? {
        return when {
            isInvalidId(productId) -> {
                InvalidProductIdException()
            }

            isInvalidId(userId) -> {
                InvalidUserIdException()
            }

            else -> {
                null
            }
        }
    }
}