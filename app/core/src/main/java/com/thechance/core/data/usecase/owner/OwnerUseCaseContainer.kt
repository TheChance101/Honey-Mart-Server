package com.thechance.core.data.usecase.owner

import org.koin.core.component.KoinComponent

data class OwnerUseCaseContainer(
    val createOwnerUseCase: CreateOwnerUseCase
) : KoinComponent
