package com.thechance.core.data.usecase.order

import org.koin.core.component.KoinComponent

data class OrderUseCasesContainer(
    val createOrderUseCase: CreateOrderUseCase,
    val getOrdersForMarketUseCase: GetOrdersForMarketUseCase,
    val cancelOrderUseCase: CancelOrderUseCase
):KoinComponent