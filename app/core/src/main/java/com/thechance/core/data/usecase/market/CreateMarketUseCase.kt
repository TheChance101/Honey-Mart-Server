package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketNameException
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class CreateMarketUseCase(private val marketService: MarketService) : KoinComponent {

    suspend operator fun invoke(marketName: String?): Market {
        return if (checkMarketName(marketName)) {
            throw InvalidMarketNameException()
        } else {
            marketService.createMarket(marketName)
        }
    }

    private fun checkMarketName(name: String?): Boolean {
        return if (name == null || !isValidMarketNameLength(name)) {
            true
        } else {
            !isValidMarketName(name)
        }
    }

    private fun isValidMarketName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }

    private fun isValidMarketNameLength(marketName: String): Boolean {
        return marketName.length in 4..14
    }
}