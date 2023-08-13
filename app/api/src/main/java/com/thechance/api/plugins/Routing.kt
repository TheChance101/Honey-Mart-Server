package com.thechance.api.plugins


import com.thechance.api.endpoints.*
import com.thechance.api.endpoints.user.*
import io.ktor.server.application.*
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
        productsRoutes()
        categoryRoutes()
        marketsRoutes()
        userRoutes()
        ownerRoutes()
        cartRoutes()
        orderRoutes()
        wishListRoutes()
        deleteAllTables()
        imageRouts()
        tokenRouts()
    }

}
