package com.thechance.core.domain.usecase.market

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import io.ktor.http.content.*
import org.koin.core.component.KoinComponent

class UpdateMarketUseCase(private val repository: HoneyMartRepository) : KoinComponent {

    suspend operator fun invoke(
        marketName: String?, description: String?, marketOwnerId: Long?, role: String?,
    ): Boolean {
        isValidMarketInfo(marketName, description)?.let { throw it }
        isValidInput(marketOwnerId, role)?.let { throw it }
        val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)
        isValidMarket(marketId)?.let { throw it }
        return repository.updateMarket(marketId!!, marketName, description)
    }

    suspend fun updateImage(marketOwnerId: Long?, role: String?, image: List<PartData>?): Boolean {
        isValidInput(marketOwnerId, role)?.let { throw it }
        isValidImage(image)?.let { throw it }
        val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)
        isValidMarket(marketId)?.let { throw it }
        val imageUrl = saveImage(image!!, name = "market$marketId", MARKET_IMAGES_PATH)
        return repository.addMarketImage(marketId!!, imageUrl)
    }

    suspend fun updateLocation(
        marketOwnerId: Long?, role: String?, address: String?, latitude: Double?, longitude: Double?
    ): Boolean {
        isValidInput(marketOwnerId, role)?.let { throw it }
        isValidUpdateLocation(latitude, longitude, address)?.let { throw it }
        val marketId = repository.getMarketIdByOwnerId(ownerId = marketOwnerId!!)
        isValidMarket(marketId)?.let { throw it }
        return repository.updateMarketLocation(marketId!!, latitude, longitude, address)
    }

    private fun isValidInput(marketOwnerId: Long?, role: String?): Exception? {
        return if (isInvalidId(marketOwnerId)) {
            InvalidMarketIdException()
        } else if (!isValidRole(MARKET_OWNER_ROLE, role)) {
            InvalidOwnerIdException()
        } else {
            null
        }
    }

    private fun isValidMarketInfo(marketName: String?, description: String?): Exception? {
        return if (marketName == null || description == null) {
            InvalidInputException()
        } else {
            null
        }
    }

    private fun isValidUpdateLocation(latitude: Double?, longitude: Double?, address: String?): Exception? {
        return if (latitude != null && longitude != null && !isValidAddress(address)) {
            null
        } else {
            InvalidInputException()
        }
    }

    private fun isValidImage(image: List<PartData>?): Exception? {
        return if (image == null) {
            InvalidInputException()
        } else {
            null
        }
    }

    private suspend fun isValidMarket(marketId: Long?): Exception? {
        return if (marketId == null) {
            InvalidMarketIdException()
        } else {
            val isDeleted = repository.isMarketDeleted(marketId)
            if (isDeleted == null) {
                IdNotFoundException()
            } else if (isDeleted) {
                MarketDeletedException()
            } else {
                null
            }
        }
    }

}