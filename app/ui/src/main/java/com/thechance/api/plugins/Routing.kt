package com.thechance.api.plugins


import com.thechance.api.endpoints.categoryRoutes
import com.thechance.api.endpoints.deleteAllTables
import com.thechance.api.endpoints.marketsRoutes
import com.thechance.api.endpoints.productsRoutes
import com.thechance.core.data.service.CategoryService
import com.thechance.core.data.service.DeleteAllTablesService
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.service.ProductService
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val productService: ProductService by inject()
    val marketService: MarketService by inject()
    val categoryService: CategoryService by inject()
    val deleteAllTablesService: DeleteAllTablesService by inject()

    routing {

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        productsRoutes(productService)
        categoryRoutes(categoryService)
        marketsRoutes(marketService)
        deleteAllTables(deleteAllTablesService)
    }
}
