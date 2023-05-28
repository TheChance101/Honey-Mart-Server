package com.the_chance.plugins


import com.the_chance.data.services.ProductService
import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketService
import com.the_chance.endpoints.marketsRoutes
import com.the_chance.data.services.CategoryService
import com.the_chance.endpoints.categoryRoutes
import com.the_chance.endpoints.productsRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.plugins.openapi.*
import io.swagger.codegen.v3.generators.html.*

fun Application.configureRouting(
    productService: ProductService,
    categoryService: CategoryService,
    marketService: MarketService,
) {
    routing {
        get("/") {
            call.respond(ServerResponse.success("Welcome to Honey Mart app"))
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        productsRoutes(productService)
        categoryRoutes(categoryService)
        marketsRoutes(marketService)
    }
}
