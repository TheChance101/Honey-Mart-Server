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
import java.io.File

class AddImageProductUseCase(private val repository: HoneyMartRepository) : KoinComponent {
    suspend operator fun invoke(marketOwnerId: Long?, role: String?, productId: Long?, image: MultiPartData): Boolean {

        isValidInput(marketOwnerId, role, productId)?.let { throw it }

        val imagesUrl = saveImagesFile(getImages(image))

        return repository.addImageProduct(imagesUrl, productId!!)
    }

    private suspend fun getImages(images: MultiPartData): List<File> {
        val files = mutableListOf<File>()

        images.forEachPart { part ->
            when (part) {
                is PartData.FileItem -> {
                    val file = File.createTempFile("product${Math.random()}", ".png")
                    part.streamProvider().use { input ->
                        file.outputStream().buffered().use { output ->
                            input.copyTo(output)
                        }
                    }
                    files.add(file)
                }

                else -> {
                    // Handle other types of parts if needed
                }
            }
            part.dispose()
        }
        return files
    }

    private fun isValidInput(marketOwnerId: Long?, role: String?, productId: Long?): Exception? {
        return when {

            isInvalidId(marketOwnerId) -> {
                InvalidOwnerIdException()
            }

            isInvalidId(productId) -> {
                InvalidProductIdException()
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