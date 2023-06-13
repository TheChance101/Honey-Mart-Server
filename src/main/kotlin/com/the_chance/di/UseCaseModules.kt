package com.the_chance.di

import com.thechance.core.domain.usecase.cart.*
import com.thechance.core.domain.usecase.category.*
import com.thechance.core.domain.usecase.market.*
import com.thechance.core.domain.usecase.order.*
import com.thechance.core.domain.usecase.owner.CreateOwnerUseCase
import com.thechance.core.domain.usecase.owner.LoginMarketOwnerUseCase
import com.thechance.core.domain.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.domain.usecase.product.*
import com.thechance.core.domain.usecase.user.CreateUserUseCase
import com.thechance.core.domain.usecase.user.UserUseCaseContainer
import com.thechance.core.domain.usecase.user.VerifyUserUseCase
import com.thechance.core.domain.usecase.wishlist.AddProductToWishListUseCase
import com.thechance.core.domain.usecase.wishlist.DeleteProductFromWishListUseCase
import com.thechance.core.domain.usecase.wishlist.GetWishListUseCase
import com.thechance.core.domain.usecase.wishlist.WishListUseCaseContainer
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val productUseCaseModule = module {
    singleOf(::ProductUseCasesContainer) { bind<ProductUseCasesContainer>() }
    singleOf(::CreateProductUseCase) { bind<CreateProductUseCase>() }
    singleOf(::DeleteProductUseCase) { bind<DeleteProductUseCase>() }
    singleOf(::UpdateProductUseCase) { bind<UpdateProductUseCase>() }
    singleOf(::UpdateProductCategoryUseCase) { bind<UpdateProductCategoryUseCase>() }
    singleOf(::GetCategoriesForProductUseCase) { bind<GetCategoriesForProductUseCase>() }
}

val marketUseCaseModule = module {
    singleOf(::MarketUseCaseContainer) { bind<MarketUseCaseContainer>() }
    singleOf(::CreateMarketUseCase) { bind<CreateMarketUseCase>() }
    singleOf(::DeleteMarketUseCase) { bind<DeleteMarketUseCase>() }
    singleOf(::GetMarketsUseCase) { bind<GetMarketsUseCase>() }
    singleOf(::UpdateMarketUseCase) { bind<UpdateMarketUseCase>() }
    singleOf(::GetCategoriesByMarketIdUseCase) { bind<GetCategoriesByMarketIdUseCase>() }
}

val categoryUseCaseModule = module {
    singleOf(::CategoryUseCasesContainer) { bind<CategoryUseCasesContainer>() }
    singleOf(::CreateCategoryUseCase) { bind<CreateCategoryUseCase>() }
    singleOf(::DeleteCategoryUseCase) { bind<DeleteCategoryUseCase>() }
    singleOf(::GetAllCategoriesUseCase) { bind<GetAllCategoriesUseCase>() }
    singleOf(::UpdateCategoryUseCase) { bind<UpdateCategoryUseCase>() }
}
val wishListUseCaseModule = module {
    singleOf(::WishListUseCaseContainer) { bind<WishListUseCaseContainer>() }
    singleOf(::AddProductToWishListUseCase) { bind<AddProductToWishListUseCase>() }
    singleOf(::GetWishListUseCase) { bind<GetWishListUseCase>() }
    singleOf(::DeleteProductFromWishListUseCase) { bind<DeleteProductFromWishListUseCase>() }
}

val orderUseCaseModule = module {
    singleOf(::OrderUseCasesContainer) { bind<OrderUseCasesContainer>() }
    singleOf(::CreateOrderUseCase) { bind<CreateOrderUseCase>() }
    singleOf(::GetOrdersForMarketUseCase) { bind<GetOrdersForMarketUseCase>() }
    singleOf(::GetOrdersForUserUseCase) { bind<GetOrdersForUserUseCase>() }
    singleOf(::UpdateOrderStateUseCase) { bind<UpdateOrderStateUseCase>() }
    singleOf(::GetOrderDetailsUseCase) { bind<GetOrderDetailsUseCase>() }
}

val userUseCaseModule = module {
    singleOf(::UserUseCaseContainer) { bind<UserUseCaseContainer>() }
    singleOf(::CreateUserUseCase) { bind<CreateUserUseCase>() }
    singleOf(::VerifyUserUseCase) { bind<VerifyUserUseCase>() }
}
val ownerUseCaseModule = module {
    singleOf(::OwnerUseCaseContainer) { bind<OwnerUseCaseContainer>() }
    singleOf(::CreateOwnerUseCase) { bind<CreateOwnerUseCase>() }
    singleOf(::LoginMarketOwnerUseCase) { bind<LoginMarketOwnerUseCase>() }
}

val cartUseCase = module {
    singleOf(::GetCartUseCase) { bind<GetCartUseCase>() }
    singleOf(::AddProductToCartUseCase) { bind<AddProductToCartUseCase>() }
    singleOf(::DeleteProductInCartUseCase) { bind<DeleteProductInCartUseCase>() }
    singleOf(::CartUseCasesContainer) { bind<CartUseCasesContainer>() }
    singleOf(::DeleteCartUseCase) { bind<DeleteCartUseCase>() }
}