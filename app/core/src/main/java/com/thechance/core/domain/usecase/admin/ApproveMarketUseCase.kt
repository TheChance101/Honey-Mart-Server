package com.thechance.core.domain.usecase.admin

import com.thechance.core.domain.repository.AuthRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.ADMIN_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import org.koin.core.component.KoinComponent

class ApproveMarketUseCase(private val repository: AuthRepository) : KoinComponent {

    suspend operator fun invoke(marketId: Long?, isApproved: Boolean, role: String?): Boolean {
        return if (!isValidRole(ADMIN_ROLE, role)) {
            throw AdminAccessDeniedException()
        } else if (isInvalidId(marketId)) {
            throw InvalidMarketIdException()
        } else {
            repository.approveMarket(marketId!!, isApproved)
        }

    }
}