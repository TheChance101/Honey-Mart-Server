package com.the_chance.di

import com.thechance.core.domain.usecase.RefreshTokenUseCase
import com.thechance.core.domain.usecase.admin.*
import com.thechance.core.domain.usecase.cart.*
import com.thechance.core.domain.usecase.category.*
import com.thechance.core.domain.usecase.coupon.*
import com.thechance.core.domain.usecase.deviceToken.SaveUserDeviceTokenUseCase
import com.thechance.core.domain.usecase.market.*
import com.thechance.core.domain.usecase.notification.*
import com.thechance.core.domain.usecase.order.*
import com.thechance.core.domain.usecase.owner.CreateOwnerUseCase
import com.thechance.core.domain.usecase.owner.GetOwnerProfileUseCase
import com.thechance.core.domain.usecase.owner.LoginOwnerUseCase
import com.thechance.core.domain.usecase.owner.OwnerUseCaseContainer
import com.thechance.core.domain.usecase.product.*
import com.thechance.core.domain.usecase.user.*
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
    singleOf(::UpdateProductImageUseCase) { bind<UpdateProductImageUseCase>() }
}

val marketUseCaseModule = module {
    singleOf(::MarketUseCaseContainer) { bind<MarketUseCaseContainer>() }
    singleOf(::CreateMarketUseCase) { bind<CreateMarketUseCase>() }
    singleOf(::DeleteMarketUseCase) { bind<DeleteMarketUseCase>() }
    singleOf(::GetMarketsUseCase) { bind<GetMarketsUseCase>() }
    singleOf(::UpdateMarketUseCase) { bind<UpdateMarketUseCase>() }
    singleOf(::GetCategoriesByMarketIdUseCase) { bind<GetCategoriesByMarketIdUseCase>() }
    singleOf(::GetMarketDetailsUseCase) { bind<GetMarketDetailsUseCase>() }
    singleOf(::CheckMarketApprovedUseCase) { bind<CheckMarketApprovedUseCase>() }
    singleOf(::UpdateMarketStatusUseCase) { bind<UpdateMarketStatusUseCase>() }
    singleOf(::GetMarketInfoUseCase) { bind<GetMarketInfoUseCase>() }
    singleOf(::RestoreMarketUseCase) { bind<RestoreMarketUseCase>() }
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
    singleOf(::LoginOwnerUseCase) { bind<LoginOwnerUseCase>() }
    singleOf(::GetOwnerProfileUseCase) { bind<GetOwnerProfileUseCase>() }
}

val adminUseCaseModule = module {
    singleOf(::VerifyAdminUseCase) { bind<VerifyAdminUseCase>() }
    singleOf(::GetMarketsRequestsDetails) { bind<GetMarketsRequestsDetails>() }
    singleOf(::ApproveMarketUseCase) { bind<ApproveMarketUseCase>() }
    singleOf(::AdminUseCaseContainer) { bind<AdminUseCaseContainer>() }
    singleOf(::AuthenticateAdminUseCase) { bind<AuthenticateAdminUseCase>() }
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
    singleOf(::SendUserNotificationOnOrderStateUseCase) { bind<SendUserNotificationOnOrderStateUseCase>() }
    singleOf(::SendOwnerNotificationUseCase) { bind<SendOwnerNotificationUseCase>() }
    singleOf(::NotificationUseCaseContainer) { bind<NotificationUseCaseContainer>() }
    singleOf(::GetUserNotificationHistoryUseCase) { bind<GetUserNotificationHistoryUseCase>() }
    singleOf(::GetOwnerNotificationHistoryUseCase) { bind<GetOwnerNotificationHistoryUseCase>() }
    singleOf(::UpdateNotificationState) { bind<UpdateNotificationState>() }
}

val deviceTokenUseCase = module {
    singleOf(::SaveUserDeviceTokenUseCase) { bind<SaveUserDeviceTokenUseCase>() }
    singleOf(::SaveUserDeviceTokenUseCase) { bind<SaveUserDeviceTokenUseCase>() }
}

val couponsUseCase = module {
    singleOf(::CouponUseCaseContainer) { bind<CouponUseCaseContainer>() }
    singleOf(::CreateCouponUseCase) { bind<CreateCouponUseCase>() }
    singleOf(::GetValidCouponsUseCase) { bind<GetValidCouponsUseCase>() }
    singleOf(::GetAllCouponsForUserUseCase) { bind<GetAllCouponsForUserUseCase>() }
    singleOf(::GetAllClippedCouponsForUserUseCase) { bind<GetAllClippedCouponsForUserUseCase>() }
    singleOf(::GetAllCouponsForMarketUseCase) { bind<GetAllCouponsForMarketUseCase>() }
    singleOf(::ClipCouponUseCase) { bind<ClipCouponUseCase>() }
    singleOf(::GetMarketProductsWithoutValidCouponsUseCase) { bind<GetMarketProductsWithoutValidCouponsUseCase>() }
    singleOf(::SearchMarketProductsWithoutValidCouponsUseCase) { bind<SearchMarketProductsWithoutValidCouponsUseCase>() }
}