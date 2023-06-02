package com.thechance.core.data.validation.market

interface MarketValidation {
    fun checkCreateValidation(marketName: String): Exception?

    fun checkUpdateValidation(marketName: String): Exception?

}