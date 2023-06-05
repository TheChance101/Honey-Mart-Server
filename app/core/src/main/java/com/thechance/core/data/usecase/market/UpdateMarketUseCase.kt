package com.thechance.core.data.usecase.market

import com.thechance.core.data.model.Market
import com.thechance.core.data.service.MarketService
import com.thechance.core.data.utils.InvalidMarketIdException
import com.thechance.core.data.utils.InvalidMarketNameException
import org.koin.core.component.KoinComponent
import java.util.regex.Pattern

class UpdateMarketUseCase(private val marketService: MarketService) : KoinComponent {
    suspend operator fun invoke(marketId: Long?, marketName: String?): Market {
        return if (checkId(marketId)) {
            throw InvalidMarketIdException()
        } else if (checkMarketName(marketName)) {
            throw InvalidMarketNameException()
        } else {
            marketService.updateMarket(marketId, marketName)
        }
    }

    private fun checkId(id: Long?): Boolean {
        return id == null
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