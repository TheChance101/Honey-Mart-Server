package com.the_chance.di

import com.thechance.core.domain.usecase.RefreshTokenUseCase
import com.thechance.core.domain.usecase.cart.AddProductToCartUseCase
import com.thechance.core.domain.usecase.cart.CartUseCasesContainer
import com.thechance.core.domain.usecase.cart.DeleteCartUseCase
import com.thechance.core.domain.usecase.cart.DeleteProductInCartUseCase
import com.thechance.core.domain.usecase.cart.GetCartUseCase
import com.thechance.core.domain.usecase.category.CategoryUseCasesContainer
import com.thechance.core.domain.usecase.category.CreateCategoryUseCase
import com.thechance.core.domain.usecase.category.DeleteCategoryUseCase
import com.thechance.core.domain.usecase.category.GetAllProductsInCategoryUseCase
import com.thechance.core.domain.usecase.category.UpdateCategoryUseCase
import com.thechance.core.domain.usecase.market.CreateMarketUseCase
import com.thechance.core.domain.usecase.market.DeleteMarketUseCase
import com.thechance.core.domain.usecase.market.GetCategoriesByMarketIdUseCase
import com.thechance.core.domain.usecase.market.GetMarketDetailsUseCase
import com.thechance.core.domain.usecase.market.GetMarketsUseCase
import com.thechance.core.domain.usecase.market.MarketUseCaseContainer
import com.thechance.core.domain.usecase.market.UpdateMarketStatusUseCase
import com.thechance.core.domain.usecase.market.UpdateMarketUseCase
import com.thechance.core.domain.usecase.order.*
import com.thechance.core.domain.usecase.owner.CreateOwnerUseCase
import com.thechance.core.domain.usecase.owner.GetOwnerProfileUseCase
import com.thechance.core.domain.usecase.owner.LoginMarketOwnerUseCase
import com.thechance.core.domain.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.domain.usecase.product.AddImageProductUseCase
import com.thechance.core.domain.usecase.product.CreateProductUseCase
import com.thechance.core.domain.usecase.product.DeleteImageFromProductUseCase
import com.thechance.core.domain.usecase.product.DeleteProductUseCase
import com.thechance.core.domain.usecase.product.GetAllProductsUseCase
import com.thechance.core.domain.usecase.product.GetCategoriesForProductUseCase
import com.thechance.core.domain.usecase.product.GetMostRecentProductsUseCase
import com.thechance.core.domain.usecase.product.GetProductDetailsUseCase
import com.thechance.core.domain.usecase.product.ProductUseCasesContainer
import com.thechance.core.domain.usecase.product.SearchProductsByNameUseCase
import com.thechance.core.domain.usecase.product.UpdateProductCategoryUseCase
import com.thechance.core.domain.usecase.product.UpdateProductUseCase
import com.thechance.core.domain.usecase.user.CreateUserUseCase
import com.thechance.core.domain.usecase.user.GetUserProfileUseCase
import com.thechance.core.domain.usecase.user.SaveUserProfileImageUseCase
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
    singleOf(::AddImageProductUseCase) { bind<AddImageProductUseCase>() }
    singleOf(::DeleteImageFromProductUseCase) { bind<DeleteImageFromProductUseCase>() }
    singleOf(::GetProductDetailsUseCase) { bind<GetProductDetailsUseCase>() }
    singleOf(::SearchProductsByNameUseCase) { bind<SearchProductsByNameUseCase>() }
    singleOf(::GetMostRecentProductsUseCase) { bind<GetMostRecentProductsUseCase>() }
    singleOf(::GetAllProductsUseCase) { bind<GetAllProductsUseCase>() }
}

val marketUseCaseModule = module {
    singleOf(::MarketUseCaseContainer) { bind<MarketUseCaseContainer>() }
    singleOf(::CreateMarketUseCase) { bind<CreateMarketUseCase>() }
    singleOf(::DeleteMarketUseCase) { bind<DeleteMarketUseCase>() }
    singleOf(::GetMarketsUseCase) { bind<GetMarketsUseCase>() }
    singleOf(::UpdateMarketUseCase) { bind<UpdateMarketUseCase>() }
    singleOf(::GetCategoriesByMarketIdUseCase) { bind<GetCategoriesByMarketIdUseCase>() }
    singleOf(::GetMarketDetailsUseCase) { bind<GetMarketDetailsUseCase>() }
    singleOf(::UpdateMarketStatusUseCase) { bind<UpdateMarketStatusUseCase>() }
}

val categoryUseCaseModule = module {
    singleOf(::CategoryUseCasesContainer) { bind<CategoryUseCasesContainer>() }
    singleOf(::CreateCategoryUseCase) { bind<CreateCategoryUseCase>() }
    singleOf(::DeleteCategoryUseCase) { bind<DeleteCategoryUseCase>() }
    singleOf(::GetAllProductsInCategoryUseCase) { bind<GetAllProductsInCategoryUseCase>() }
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
    singleOf(::SaveUserProfileImageUseCase) { bind<SaveUserProfileImageUseCase>() }
    singleOf(::GetUserProfileUseCase) { bind<GetUserProfileUseCase>() }

}

val ownerUseCaseModule = module {
    singleOf(::OwnerUseCaseContainer) { bind<OwnerUseCaseContainer>() }
    singleOf(::CreateOwnerUseCase) { bind<CreateOwnerUseCase>() }
    singleOf(::LoginMarketOwnerUseCase) { bind<LoginMarketOwnerUseCase>() }
    singleOf(::GetOwnerProfileUseCase) { bind<GetOwnerProfileUseCase>() }
}

val cartUseCase = module {
    singleOf(::GetCartUseCase) { bind<GetCartUseCase>() }
    singleOf(::AddProductToCartUseCase) { bind<AddProductToCartUseCase>() }
    singleOf(::DeleteProductInCartUseCase) { bind<DeleteProductInCartUseCase>() }
    singleOf(::CartUseCasesContainer) { bind<CartUseCasesContainer>() }
    singleOf(::DeleteCartUseCase) { bind<DeleteCartUseCase>() }
}

val tokenUseCase = module {
    singleOf(::RefreshTokenUseCase) { bind<RefreshTokenUseCase>() }
}

val notificationUseCase = module {
    singleOf(::SendNotificationOnOrderStateUseCase) { bind<SendNotificationOnOrderStateUseCase>() }
    singleOf(::GetNotificationHistoryUseCase) { bind<GetNotificationHistoryUseCase>() }
    singleOf(::SaveNotificationUseCase) { bind<SaveNotificationUseCase>() }
}