package com.thechance.api.plugins


import com.thechance.api.endpoints.categoryRoutes
import com.thechance.api.endpoints.deleteAllTables
import com.thechance.api.endpoints.marketsRoutes
import com.thechance.api.endpoints.productsRoutes
import com.thechance.core.data.service.DeleteAllTablesService
import com.thechance.core.data.service.ProductService
import com.thechance.core.data.usecase.category.CategoryUseCasesContainer
import com.thechance.core.data.usecase.market.MarketUseCaseContainer
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val productService: ProductService by inject()
    val categoryUseCasesContainer: CategoryUseCasesContainer by inject()
    val marketUseCasesContainer: MarketUseCaseContainer by inject()
    val deleteAllTablesService: DeleteAllTablesService by inject()

    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        productsRoutes(productService)
        categoryRoutes(categoryUseCasesContainer)
        marketsRoutes(marketUseCasesContainer)
        deleteAllTables(deleteAllTablesService)
    }
}
