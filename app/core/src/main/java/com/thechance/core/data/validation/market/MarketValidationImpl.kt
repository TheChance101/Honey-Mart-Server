package com.thechance.core.data.validation.market

import com.thechance.api.utils.InvalidInputException
import java.util.regex.Pattern

class MarketValidationImpl : MarketValidation {

    override fun checkId(id: Long?): Exception? {
        return if (id == null) {
            InvalidInputException("Invalid product Id")
        } else {
            null
        }
    }

    override fun checkMarketName(name: String?): Exception? {
        return if (name == null || !isValidMarketNameLength(name)) {
            InvalidInputException("Market Name is required and should be between 4 and 14 characters")
        } else {
            if (!isValidMarketName(name)) {
                InvalidInputException("Invalid market name. Just can contain text and numbers")
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