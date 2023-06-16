package com.thechance.core.domain.usecase.user

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.utils.*
import org.koin.core.component.KoinComponent
import java.io.File

class GetUserProfileImageUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(userId: Long?, role: String?): String {
        val imageUrl = repository.getUserProfileImage(userId!!)
        isInvalidInput(imageUrl, userId, role)
        val path = getInternalImagePath(imageUrl!!)
        val file = File(path)
        return if (file.exists()) {
            imageUrl
        } else {
            throw ImageNotFoundException()
        }
    }
}

private fun getInternalImagePath(url: String): String {
    return url.substringAfter("://").substringAfter("/")
}

private fun isInvalidInput(imageUrl: String?, userId: Long?, role: String?): Exception? {
    return when {
        !isValidRole(NORMAL_USER_ROLE, role) -> {
            throw InvalidUserIdException()
        }

        isInvalidId(userId) -> {
            throw InvalidUserIdException()
        }

        imageUrl.isNullOrEmpty() -> {
            throw ImageNotFoundException()
        }

        else -> {
            null
        }
    }
}