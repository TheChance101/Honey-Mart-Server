package com.thechance.core.data.usecase.wishlist

import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.*
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
                   true
                    //delete
                }
                   else {
                        repository.addToWishList(
                            wishListId = getWishListId(userId),
                            productId = productId
                        )
                    }
                }
            }
        }

    private suspend fun getWishListId(userId: Long): Long {
        return repository.getWishListId(userId) ?: repository.createWishList(userId)
    }
}