package com.thechance.core.data.datasource.database.tables.market

import org.koin.core.component.KoinComponent

data class MarketUseCaseContainer(
    val createMarketUseCase: CreateMarketUseCase,
    val updateMarketUseCase: UpdateMarketUseCase,
    val deleteMarketUseCase: DeleteMarketUseCase,
    val getMarketsUseCase: GetMarketsUseCase,
    val getCategoriesByMarketIdUseCase: GetCategoriesByMarketIdUseCase,
    val getMarketDetailsUseCase: GetMarketDetailsUseCase
) : KoinComponent
