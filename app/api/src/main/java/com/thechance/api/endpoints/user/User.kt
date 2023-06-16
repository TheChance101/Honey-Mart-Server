package com.thechance.api.endpoints.user

import com.thechance.api.ServerResponse
import com.thechance.core.domain.usecase.user.UserUseCaseContainer
import com.thechance.core.utils.ROLE_TYPE
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {

    val userUseCasesContainer: UserUseCaseContainer by inject()

    route("/user") {

        post("/signup") {
            val params = call.receiveParameters()
            val password = params["password"]?.trim()
            val fullName = params["fullName"]?.trim()
            val email = params["email"]?.trim()

            userUseCasesContainer.createUserUseCase(password = password, fullName = fullName, email = email)
            call.respond(HttpStatusCode.Created, ServerResponse.success("user created successfully"))
        }

        post("/login") {
            val params = call.receiveParameters()
            val email = params["email"]?.trim().toString()
            val password = params["password"]?.trim().toString()

            val token = userUseCasesContainer.verifyUserUseCase(email, password)
            call.respond(HttpStatusCode.Created, ServerResponse.success(token, "Logged in Successfully"))
        }
        authenticate {
            post("/profileImage") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val imageParts = call.receiveMultipart().readAllParts()
                val imageUrl = userUseCasesContainer.saveUserProfileImageUseCase(imageParts, userId, role)
                call.respond(HttpStatusCode.Created, ServerResponse.success("Added successfully", imageUrl))
            }
            get("/profileImage") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject?.toLongOrNull()
                val role = principal?.getClaim(ROLE_TYPE, String::class)
                val image = userUseCasesContainer.getUserProfileImageUseCase(userId, role)
                call.respond(HttpStatusCode.Created, ServerResponse.success("Get Image successfully", image))
            }
        }
    }
}
