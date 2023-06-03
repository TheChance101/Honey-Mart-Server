package com.thechance.core.data.validation.market

interface MarketValidation {
    fun checkId(id: Long?): Exception?

    fun checkMarketName(name: String?): Exception?

}