package com.thechance.core.data.usecase.wishlist

import com.thechance.core.data.model.ProductInWishList
import com.thechance.core.data.repository.HoneyMartRepository
import com.thechance.core.data.utils.InvalidUserIdException
import com.thechance.core.data.utils.isInvalidId
import org.koin.core.component.KoinComponent

class GetWishListUseCase(private val repository: HoneyMartRepository) :
    KoinComponent {

    suspend operator fun invoke(userId: Long?): List<ProductInWishList> {
        return if (isInvalidId(userId)) {
            throw InvalidUserIdException()
        } else {
            repository.getWishList(getWishListId(userId!!))
        }
    }

    private suspend fun getWishListId(userId: Long): Long {
        return repository.getWishListId(userId) ?: repository.createWishList(userId)
    }
}