package com.thechance.core.data.validation.category

import org.jetbrains.exposed.sql.ResultRow

interface CategoryValidation {
    fun checkCreateValidation(categoryName: String, categoryImage: String, category: ResultRow?): List<String>
}