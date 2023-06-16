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
        val imageUrl = saveImage(imageParts, userId)
        isInvalidInput(imageUrl, userId, role)
        return if (repository.saveUserProfileImage(imageUrl, userId!!)) {
            imageUrl
        } else {
            throw AddImageFailedException()
        }
    }

    private fun saveImage(imageParts: List<PartData>, userId: Long?): String {
        val image = imageParts.filterIsInstance<PartData.FileItem>().firstOrNull()
        if (image != null) {
            val imageName = "$userId.jpg"
            val imageBytes = image.streamProvider().readBytes()

            // Save the image to the server
            val uploadDir = File(USER_IMAGES_PATH)
            uploadDir.mkdirs() // Create the directory if it doesn't exist
            File(uploadDir, imageName).writeBytes(imageBytes)
            return "$BASE_URL/$USER_IMAGES_PATH/$imageName"
        } else {
            throw AddImageFailedException()
        }
    }

    private fun isInvalidInput(imageUrl: String, userId: Long?, role: String?): Exception? {
        return when {
            !isValidRole(NORMAL_USER_ROLE, role) -> {
                throw InvalidUserIdException()
            }

            isInvalidId(userId) -> {
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