package com.the_chance.di

import com.thechance.api.service.*
import com.thechance.core.data.database.CoreDataBase
import com.thechance.core.data.database.CoreDataBaseImp
import com.thechance.core.data.service.*
import com.thechance.core.data.validation.category.CategoryValidation
import com.thechance.core.data.validation.category.CategoryValidationImpl
import com.thechance.core.data.validation.market.MarketValidation
import com.thechance.core.data.validation.market.MarketValidationImpl
import com.thechance.core.data.validation.product.ProductValidation
import com.thechance.core.data.validation.product.ProductValidationImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val myModule = module {
    single<CoreDataBase> { CoreDataBaseImp() }
    singleOf(::ProductServiceImp) { bind<ProductService>() }
    singleOf(::CategoryServiceImp) { bind<CategoryService>() }
    singleOf(::MarketServiceImp) { bind<MarketService>() }
    single<MarketCategoriesService> { MarketCategoriesServiceImp() }
    single<ProductValidation> { ProductValidationImpl() }
    single<MarketValidation> { MarketValidationImpl() }
    single<CategoryValidation> { CategoryValidationImpl() }
    single<DeleteAllTablesService> { DeleteAllTablesServiceServiceImp() }
}