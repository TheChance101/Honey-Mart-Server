package com.thechance.core.data.validation.market

import com.thechance.core.data.utils.InvalidInputException
import java.util.regex.Pattern

class MarketValidationImpl : MarketValidation {

    override fun checkId(id: Long?): Exception? {
        return if (id == null) {
            InvalidInputException()
        } else {
            null
        }
    }

    override fun checkMarketName(name: String?): Exception? {
        return if (name == null || !isValidMarketNameLength(name)) {
            InvalidInputException()
        } else {
            if (!isValidMarketName(name)) {
                InvalidInputException()
            } else {
                null
            }
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