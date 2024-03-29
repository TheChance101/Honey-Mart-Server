package com.thechance.core.domain.usecase.owner

import org.koin.core.component.KoinComponent

data class OwnerUseCaseContainer(
    val createOwnerUseCase: CreateOwnerUseCase,
    val verifyMarketOwnerUseCase: LoginOwnerUseCase,
    val getOwnerProfileUseCase: GetOwnerProfileUseCase
) : KoinComponent
