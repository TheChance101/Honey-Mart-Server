package com.thechance.api.plugins


import com.thechance.api.endpoints.categoryRoutes
import com.thechance.api.endpoints.marketsRoutes
import com.thechance.api.endpoints.orderRoutes
import com.thechance.api.endpoints.productsRoutes
import com.thechance.api.endpoints.*
import com.thechance.core.data.datasource.database.CoreDataBase
import com.thechance.core.domain.usecase.DeleteAllTablesUseCase
import com.thechance.core.data.usecase.cart.CartUseCasesContainer
import com.thechance.core.data.usecase.cart.GetCartUseCase
import com.thechance.core.data.usecase.category.CategoryUseCasesContainer
import com.thechance.core.data.usecase.market.MarketUseCaseContainer
import com.thechance.core.data.usecase.order.OrderUseCasesContainer
import com.thechance.core.data.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.data.usecase.product.ProductUseCasesContainer
import com.thechance.core.data.usecase.user.UserUseCaseContainer
import com.thechance.core.data.usecase.wishlist.WishListUseCaseContainer
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import org.koin.ktor.ext.inject

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
    }
}
