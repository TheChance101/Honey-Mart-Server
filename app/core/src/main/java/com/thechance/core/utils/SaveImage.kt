package com.thechance.core.utils

import io.ktor.http.content.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


internal fun saveImage(imageParts: List<PartData>, name: String, imagePath: String): String {
    val image = imageParts.filterIsInstance<PartData.FileItem>().firstOrNull()
    if (image != null) {
        val imageName = "$name.jpg"
        val imageBytes = image.streamProvider().readBytes()
        val uploadDir = File(imagePath)
        uploadDir.mkdirs()
        File(uploadDir, imageName).writeBytes(imageBytes)
        return "$BASE_URL/$imagePath/$imageName"
    } else {
        throw AddImageFailedException()
    }
}
