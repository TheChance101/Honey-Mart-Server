package com.thechance.core.utils

import io.ktor.http.content.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


internal fun saveImage(imageParts: List<PartData>, name: String): String {
    val image = imageParts.filterIsInstance<PartData.FileItem>().firstOrNull()
    if (image != null) {
        val imageName = "$name.jpg"
        val imageBytes = image.streamProvider().readBytes()

        // Save the image to the server
        val uploadDir = File(IMAGES_PATH)
        uploadDir.mkdirs() // Create the directory if it doesn't exist
        File(uploadDir, imageName).writeBytes(imageBytes)
        return "$BASE_URL/$IMAGES_PATH/$imageName"
    } else {
        throw AddImageFailedException()
    }
}
