package com.thechance.core.data.validation.market

import com.thechance.api.utils.InvalidInputException
import java.util.regex.Pattern

class MarketValidationImpl : MarketValidation {
    override fun checkCreateValidation(marketName: String): Exception? {
        val exception = mutableListOf<String>()
        if (!isValidMarketName(marketName)) {
            exception.add("Invalid market name. Just can contain text and numbers")
        }
        if (!checkNameLength(marketName)) {
            exception.add("Market name length should be between 4 and 14 characters")
        }
        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString("\n"))
        }
    }

    override fun checkUpdateValidation(marketName: String): Exception? {
        val exception = mutableListOf<String>()
        if (!isValidMarketName(marketName)) {
            exception.add("Invalid market name. Just can contain text and numbers")
        }
        if (!checkNameLength(marketName)) {
            exception.add("Market name length should be between 4 and 14 characters")
        }
        return if (exception.isEmpty()) {
            null
        } else {
            InvalidInputException(exception.joinToString("\n"))
        }
    }


    private fun isValidMarketName(name: String): Boolean {
        val pattern = Pattern.compile("^[a-zA-Z0-9]+(\\s[a-zA-Z0-9]+)*$")
        val matcher = pattern.matcher(name)
        return matcher.matches()
    }

    private fun checkNameLength(marketName: String): Boolean {
        return marketName.length in 4..14
    }
}