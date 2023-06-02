package com.thechance.core.data.validation.market

interface MarketValidation {
    fun isValidMarketName(name: String): Boolean
    fun checkCreateValidation(marketName: String): Exception?
    fun checkNameLength(marketName: String): Boolean

}