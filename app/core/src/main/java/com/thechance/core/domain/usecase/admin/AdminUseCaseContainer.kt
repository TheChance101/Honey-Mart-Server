package com.thechance.core.domain.usecase.admin

import org.koin.core.component.KoinComponent

data class AdminUseCaseContainer (
    val getMarketsRequestsDetails: GetMarketsRequestsDetails,
    val approveMarketUseCase: ApproveMarketUseCase,
    val verifyAdminUseCase: VerifyAdminUseCase,
    val authenticateAdminUseCase: AuthenticateAdminUseCase
): KoinComponent