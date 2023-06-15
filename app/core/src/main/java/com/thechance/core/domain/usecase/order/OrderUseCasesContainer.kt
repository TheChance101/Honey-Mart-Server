package com.thechance.core.domain.usecase.order

import org.koin.core.component.KoinComponent

data class OrderUseCasesContainer(
    val createOrderUseCase: CreateOrderUseCase,
    val getOrdersForMarketUseCase: GetOrdersForMarketUseCase,
    val getOrdersForUserUseCase: GetOrdersForUserUseCase,
    val updateOrderStateUseCase: UpdateOrderStateUseCase,
    val getOrderDetailsUseCase: GetOrderDetailsUseCase
) : KoinComponent