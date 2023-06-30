package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.io.File

class DeleteImageFromProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketOwnerId: Long?, role: String?, productId: Long?, imageId: Long?): Boolean {

        isValidInput(marketOwnerId, role, productId, imageId)?.let { throw it }
        return if (isMarketOwner(marketOwnerId!!, productId!!)) {
            val path = repository.deleteImageFromProduct(productId = productId, imageId = imageId!!)
            val file = File(extractFilePath(path))
            if (file.exists()) {
                file.delete()
            }
            true
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

    private fun extractFilePath(url: String): String {
        val prefix = "$BASE_URL/"
        return url.substringAfterLast(prefix)
    }

    private suspend fun isMarketOwner(marketOwnerId: Long, productId: Long): Boolean {
        val marketId = repository.getMarketId(productId)
        return marketId?.let { repository.getOwnerIdByMarketId(it) } == marketOwnerId
    }
}