package com.thechance.core.domain.usecase.coupon

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateCouponUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(
        ownerId: Long?,
        role: String?,
        productId: Long?,
        count: Int?,
        discountPercentage: Double?,
        expirationDate: String?
    ): Boolean {
        isValidInput(ownerId, role, productId, count, discountPercentage, expirationDate)
        return repository.addCoupon(
            repository.getMarketIdByOwnerId(ownerId!!)!!,
            productId!!,
            count!!,
            discountPercentage!!,
            parseStringToDate(expirationDate!!)
        )
    }

    private suspend fun isValidInput(
        ownerId: Long?,
        role: String?,
        productId: Long?,
        count: Int?,
        discountPercentage: Double?,
        expirationDate: String?
    ): Exception? {
        val isProductDeleted = repository.isProductDeleted(productId!!)
        return when {
            isProductDeleted == null -> {
                throw IdNotFoundException()
            }

            isProductDeleted -> {
                throw ProductDeletedException()
            }

            isInvalidId(productId) -> {
                InvalidProductIdException()
            }

            isInvalidId(ownerId) -> {
                InvalidOwnerIdException()
            }

            !isOwnerOfProduct(ownerId!!, productId) -> {
                UnauthorizedException()
            }

            isInvalidNumber(count) -> {
                InvalidCountException()
            }

            isInvalidPercentage(discountPercentage) -> {
                InvalidPercentage()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            isInvalidDate(expirationDate) -> {
                InvalidExpirationDateException()
            }

            else -> {
                null
            }
        }
    }

    private suspend fun isOwnerOfProduct(ownerId: Long, productId: Long): Boolean {
        val marketId = repository.getProductMarketId(productId)
        return repository.getOwnerIdByMarketId(marketId) == ownerId
    }

    private fun parseStringToDate(date: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.parse(date, formatter)
    }
}