package com.the_chance.plugins


import com.the_chance.data.services.ProductService
import com.the_chance.data.ServerResponse
import com.the_chance.data.services.MarketService
import com.the_chance.endpoints.marketsRoutes
import com.the_chance.data.services.CategoryService
import com.the_chance.data.services.MarketCategoriesService
import com.the_chance.endpoints.categoryRoutes
import com.the_chance.endpoints.marketCategoriesRoutes
import com.the_chance.endpoints.productsRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(
    productService: ProductService,
    categoryService: CategoryService,
    marketService: MarketService,
    marketCategoriesService: MarketCategoriesService
) {
    routing {
        get("/") {
            call.respond(ServerResponse.success("Welcome to Honey Mart app"))
        }
        productsRoutes(productService)
        categoryRoutes(categoryService)
        marketsRoutes(marketService)
        marketCategoriesRoutes(marketCategoriesService)
    }
}
