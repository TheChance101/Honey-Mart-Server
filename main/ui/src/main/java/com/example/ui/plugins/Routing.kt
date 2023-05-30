package com.example.ui.plugins


import com.example.ui.ServerResponse
import com.example.ui.service.ProductService
import com.example.ui.service.MarketService
import com.example.ui.endpoints.marketsRoutes
import com.example.ui.service.CategoryService
import com.example.ui.service.MarketCategoriesService
import com.example.ui.endpoints.categoryRoutes
import com.example.ui.endpoints.marketCategoriesRoutes
import com.example.ui.endpoints.productsRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.plugins.openapi.*
import io.swagger.codegen.v3.generators.html.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val productService: ProductService by inject()
    val marketService: MarketService by inject()
    val categoryService: CategoryService by inject()
    val marketCategoriesService: MarketCategoriesService by inject()

    routing {
        get("/") {
            call.respond(ServerResponse.success("Welcome to Honey Mart app"))
        }
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        productsRoutes(productService)
        categoryRoutes(categoryService)
        marketsRoutes(marketService)
        marketCategoriesRoutes(marketCategoriesService)
    }
}
