package com.thechance.core.data.usecase.user

import org.koin.core.component.KoinComponent

data class UserUseCaseContainer(
    val createUserUseCase: CreateUserUseCase,
) : KoinComponent
