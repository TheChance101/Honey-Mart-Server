package com.thechance.core.domain.usecase.market

import org.koin.core.component.KoinComponent

data class MarketUseCaseContainer(
    val createMarketUseCase: CreateMarketUseCase,
    val updateMarketUseCase: UpdateMarketUseCase,
    val deleteMarketUseCase: DeleteMarketUseCase,
    val getMarketsUseCase: GetMarketsUseCase,
    val getCategoriesByMarketIdUseCase: GetCategoriesByMarketIdUseCase
) : KoinComponent
