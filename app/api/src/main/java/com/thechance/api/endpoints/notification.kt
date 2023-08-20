package com.thechance.api.endpoints

import com.thechance.api.ServerResponse
import com.thechance.api.model.mapper.toApiNotification
import com.thechance.core.domain.usecase.notification.NotificationUseCaseContainer
import com.thechance.core.utils.API_KEY_AUTHENTICATION
import com.thechance.core.utils.JWT_AUTHENTICATION
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.notificationRoutes() {

    val notificationUseCase: NotificationUseCaseContainer by inject()

    route("/notification") {
        authenticate(API_KEY_AUTHENTICATION) {
            put("/{id}/update") {
                val params = call.receiveParameters()
                val notificationId = call.parameters["id"]?.trim()?.toLongOrNull()
                val isRead = params["isRead"]?.toBoolean()
                notificationUseCase.updateNotificationState(notificationId, isRead)
                call.respond(
                    HttpStatusCode.OK,
                    ServerResponse.success(true, "notification updated successfully")
                )
            }
        }
        authenticate(JWT_AUTHENTICATION) {
            get("/userNotifications") {
                val principal = call.principal<JWTPrincipal>()
                val ownerId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val notifications =
                    notificationUseCase.getNotificationHistoryUseCase(ownerId, role).map { it.toApiNotification() }
                call.respond(HttpStatusCode.OK, ServerResponse.success(notifications))
            }
        }
    }

}