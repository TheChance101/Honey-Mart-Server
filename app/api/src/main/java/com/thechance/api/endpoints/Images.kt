package com.thechance.api.endpoints

import com.thechance.core.utils.MARKET_IMAGES_PATH
import com.thechance.core.utils.USER_IMAGES_PATH
import com.thechance.core.utils.PRODUCT_IMAGES_PATH
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Route.imageRouts() {

    route("files") {
        get("/image_uploads/{image}") {
            val fileName = call.parameters["image"].toString()
            val file = File("$USER_IMAGES_PATH/$fileName")
            if (file.exists()) {
                call.respondFile(file)
            } else {
                call.respondText("File not found", status = HttpStatusCode.NotFound)
            }
        }
    }

    get("$PRODUCT_IMAGES_PATH/{marketId}/{image}") {
        val fileName = call.parameters["image"].toString()
        val marketId = call.parameters["marketId"].toString()
        val file = File("$PRODUCT_IMAGES_PATH/$marketId/$fileName")
        if (file.exists()) {
            call.respondFile(file)
        } else {
            call.respondText("File not found", status = HttpStatusCode.NotFound)
        }
    }

    get("$MARKET_IMAGES_PATH/{image}") {
        val fileName = call.parameters["image"].toString()
        val file = File("$MARKET_IMAGES_PATH/$fileName")
        if (file.exists()) {
            call.respondFile(file)
        } else {
            call.respondText("File not found", status = HttpStatusCode.NotFound)
        }
    }
}
