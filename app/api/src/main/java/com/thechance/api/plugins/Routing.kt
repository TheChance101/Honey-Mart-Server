package com.thechance.api.plugins


import com.thechance.api.endpoints.*
import com.thechance.api.endpoints.user.cartRoutes
import com.thechance.api.endpoints.user.userRoutes
import com.thechance.api.endpoints.user.wishListRoutes
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen

fun Application.configureRouting() {
    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }

        install(CachingHeaders) {
            options { call, content ->
                when (content.contentType?.withoutParameters()) {
                    ContentType.Text.Plain -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 3600))
                    ContentType.Text.Html -> CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 60))
                    else -> null
                }
            }
        }


        productsRoutes()
        categoryRoutes()
        marketsRoutes()
        userRoutes()
        ownerRoutes()
        cartRoutes()
        orderRoutes()
        wishListRoutes()
        deleteAllTables()
    }
}
