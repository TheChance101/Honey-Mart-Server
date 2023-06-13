package com.thechance.api.plugins


import com.thechance.api.utils.handleException
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusExceptions() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            handleException(cause, call)
        }
    }
}

