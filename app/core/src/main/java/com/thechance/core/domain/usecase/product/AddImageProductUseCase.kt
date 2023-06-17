package com.thechance.core.domain.usecase.product

import com.thechance.core.domain.repository.HoneyMartRepository
import com.thechance.core.entity.Image
import com.thechance.core.utils.*
import com.thechance.core.utils.MARKET_OWNER_ROLE
import com.thechance.core.utils.isInvalidId
import com.thechance.core.utils.isValidRole
import com.thechance.core.utils.saveImage
import io.ktor.http.content.*
import org.koin.core.component.KoinComponent

class AddImageProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketOwnerId: Long?, role: String?, image: List<PartData>): Image {

        isValidInput(marketOwnerId, role)?.let { throw it }

        val imageUrl = saveImage(imageParts = image, "test${Math.random()}")

        return repository.addImageProduct(imageUrl)
    }

    private fun isValidInput(marketOwnerId: Long?, role: String?): Exception? {
        return when {

            isInvalidId(marketOwnerId) -> {
                InvalidOwnerIdException()
            }

            !isValidRole(MARKET_OWNER_ROLE, role) -> {
                InvalidOwnerIdException()
            }

            else -> {
                null
            }
        }
    }

}