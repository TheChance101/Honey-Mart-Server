package com.thechance.core.domain.usecase.user

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import com.thechance.core.utils.NORMAL_USER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import io.ktor.http.content.*
import org.koin.core.component.KoinComponent
import java.io.File

class SaveUserProfileImageUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(imageParts: List<PartData>, userId: Long?, role: String?): String {
        return if (userId != null) {
            val imageUrl = saveImage(imageParts, userId.toString())

            isInvalidInput(imageUrl, role)

            if (repository.saveUserProfileImage(imageUrl, userId)) {
                imageUrl
            } else {
                throw AddImageFailedException()
            }
        } else {
            throw InvalidUserIdException()
        }

    }

    private fun isInvalidInput(imageUrl: String, role: String?): Exception? {
        return when {
            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                throw InvalidUserIdException()
            }

            imageUrl.isEmpty() -> {
                throw AddImageFailedException()
            }

            else -> {
                null
            }
        }
    }
}