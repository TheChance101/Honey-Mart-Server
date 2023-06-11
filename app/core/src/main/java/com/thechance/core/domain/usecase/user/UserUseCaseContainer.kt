package com.thechance.core.domain.usecase.user

import org.koin.core.component.KoinComponent

data class UserUseCaseContainer(
    val createUserUseCase: CreateUserUseCase,
    val verifyUserUseCase: VerifyUserUseCase,
) : KoinComponent
