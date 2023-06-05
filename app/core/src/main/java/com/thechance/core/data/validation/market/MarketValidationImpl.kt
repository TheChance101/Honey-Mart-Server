package com.thechance.core.data.validation.market

import com.thechance.core.data.utils.InvalidInputException
import com.thechance.core.data.utils.MarketNameNotFound
import java.util.regex.Pattern

class MarketValidationImpl : MarketValidation {

    override fun checkId(id: Long?): Exception? {
        return if (id == null) {
            InvalidInputException("this Id $id not available")
        } else {
            null
        }
    }

    override fun checkMarketName(name: String?): Exception? {
        return if (name == null || !isValidMarketNameLength(name)) {
            InvalidInputException("The name should be between 4 to 14 letter")
        } else {
            if (!isValidMarketName(name)) {
                InvalidInputException("The name should be only letters and numbers")
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