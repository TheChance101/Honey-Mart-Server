package com.thechance.api.plugins


import com.thechance.api.endpoints.*
import com.thechance.core.data.usecase.category.CategoryUseCasesContainer
import com.thechance.core.data.usecase.market.MarketUseCaseContainer
import com.thechance.core.data.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.data.usecase.product.ProductUseCasesContainer
import com.thechance.core.data.usecase.user.UserUseCaseContainer
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val categoryUseCasesContainer: CategoryUseCasesContainer by inject()
    val marketUseCasesContainer: MarketUseCaseContainer by inject()
    val productUseCasesContainer: ProductUseCasesContainer by inject()
    val userUseCasesContainer: UserUseCaseContainer by inject()
    val ownerUseCaseContainer: OwnerUseCaseContainer by inject()

    routing {
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml") {
            version = "4.15.5"
        }
        openAPI(path = "openapi", swaggerFile = "openapi/documentation.yaml") {
            codegen = StaticHtmlCodegen()
        }
        productsRoutes(productUseCasesContainer)
        categoryRoutes(categoryUseCasesContainer)
        marketsRoutes(marketUseCasesContainer)
        userRoutes(userUseCasesContainer)
        ownerRoutes(ownerUseCaseContainer)
    }
}
