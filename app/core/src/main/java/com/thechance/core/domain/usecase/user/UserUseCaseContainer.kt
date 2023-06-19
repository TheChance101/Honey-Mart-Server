package com.thechance.core.domain.usecase.user

import org.koin.core.component.KoinComponent

data class UserUseCaseContainer(
    val createUserUseCase: CreateUserUseCase,
    val verifyUserUseCase: VerifyUserUseCase,
    val saveUserProfileImageUseCase: SaveUserProfileImageUseCase,
    val getUserProfileImageUseCase: GetUserProfileImageUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase
) : KoinComponent
