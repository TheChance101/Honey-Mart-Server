package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent

class DeleteImageFromProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketOwnerId: Long?, role: String?, productId: Long?, imageId: Long?): Boolean {

        isValidInput(marketOwnerId, role, productId, imageId)?.let { throw it }

        return if (isMarketOwner(marketOwnerId!!, productId!!)) {
            repository.deleteImageFromProduct(productId = productId, imageId = imageId!!)
        } else {
            throw UnauthorizedException()
        }

    }

    private fun isValidInput(marketOwnerId: Long?, role: String?, productId: Long?, imageId: Long?): Exception? {
        return when {
            isInvalidId(productId) -> {
                InvalidProductIdException()
            }

            isInvalidId(imageId) -> {
                Exception()
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

    private suspend fun isMarketOwner(marketOwnerId: Long, productId: Long): Boolean {
        val marketId = repository.getMarketId(productId)
        return marketId?.let { repository.getOwnerIdByMarketId(it) } == marketOwnerId
    }
}