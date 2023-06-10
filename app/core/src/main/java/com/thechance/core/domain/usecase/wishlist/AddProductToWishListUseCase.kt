package com.thechance.core.domain.usecase.wishlist

import com.thechance.core.data.usecase.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
import com.thechance.core.utils.*
import com.thechance.core.utils.isInvalidId
import org.koin.core.component.KoinComponent

class AddProductToWishListUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(userId: Long?, productId: Long?): Boolean {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else if (isInvalidId(productId)) {
            throw InvalidProductIdException()
        } else {
            val isProductDeleted = repository.isProductDeleted(productId!!)
            if (isProductDeleted == null) {
                throw IdNotFoundException()
            } else if (isProductDeleted) {
                throw ProductDeletedException()
            } else {
                if (repository.isProductInWishList(userId!!, productId)) {
                    throw ProductAlreadyInWishListException()
                } else {
                    repository.addToWishList(getWishListId(userId), productId)
                }
            }
        }
    }

    private suspend fun getWishListId(userId: Long): Long {
        return repository.getWishListId(userId) ?: repository.createWishList(userId)
    }
}