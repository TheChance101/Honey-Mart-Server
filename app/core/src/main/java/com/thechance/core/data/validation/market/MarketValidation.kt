package com.thechance.core.data.validation.market

import com.thechance.api.model.Market

interface MarketValidation {
    fun createMarket(marketName:String):Market
}