package com.thechance.api.endpoints.user

import com.thechance.api.model.NotificationModel
import com.thechance.core.domain.usecase.market.SendNotificationByTokenUseCase
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.notificationRoutes() {

    val notificationUseCase: SendNotificationByTokenUseCase by inject()

    route("/notification"){

        authenticate(API_KEY_AUTHENTICATION) {
        post("/send") {
                val notification = call.receive<NotificationModel>()
                val response = notificationUseCase(notification.tokens,notification.title,notification.title).toString()
                call.respond("$response")
            }
        }


    }

}