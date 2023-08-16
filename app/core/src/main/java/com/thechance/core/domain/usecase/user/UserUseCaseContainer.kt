package com.thechance.core.domain.usecase.user

import org.koin.core.component.KoinComponent
import com.thechance.core.domain.usecase.user.VerifyUserUseCase

data class UserUseCaseContainer(
    val createUserUseCase: CreateUserUseCase,
    val verifyUserUseCase: VerifyUserUseCase,
    val saveUserProfileImageUseCase: SaveUserProfileImageUseCase,
    val getUserProfileUseCase: GetUserProfileUseCase
) : KoinComponent
