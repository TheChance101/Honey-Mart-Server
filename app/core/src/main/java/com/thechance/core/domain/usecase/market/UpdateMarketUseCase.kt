package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import io.ktor.http.content.*
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        marketName: String?, marketOwnerId: Long?, role: String?, image: List<PartData>?
    ): Boolean {
        isValidInput(marketName, marketOwnerId, role, image)?.let { throw it }

        val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)
        val imageUrl: String?

        return if (marketId == null) {
            throw InvalidMarketIdException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId)
            if (isDeleted == null) {
                throw IdNotFoundException()
            } else if (isDeleted) {
                throw MarketDeletedException()
            } else if (image != null) {
                imageUrl = saveImage(image, name = "market$marketId", MARKET_IMAGES_PATH)
                repository.updateMarket(marketId, imageUrl = imageUrl, marketName = null)
            } else {
                repository.updateMarket(marketId, marketName = marketName, imageUrl = null)
            }
        }
    }


    private fun isValidInput(
        marketName: String?, marketOwnerId: Long?, role: String?, image: List<PartData>?
    ): Exception? {
        return if (marketName == null && image == null) {
            InvalidInputException()
        } else if (isInvalidId(marketOwnerId)) {
            InvalidMarketIdException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

}