package com.example.core.data.validation

import org.jetbrains.exposed.sql.ResultRow

class CategoryValidation {

    fun checkCreateValidation(categoryName: String, categoryImage: String, category: ResultRow?): List<String> {
        val errorList = mutableListOf<String>()

        when {
            category != null -> errorList.add("This category with name $categoryName already exist.")
            categoryImage.isEmpty() -> errorList.add("All field is required...")
            checkNameLength(categoryName) -> errorList.add("Category name should be more than 4 and shorter than 20 characters...")
//            checkLetter(categoryName) -> errorList.add("category name should contain letters without numbers or symbols.")
        }
        return errorList
    }


    private fun checkNameLength(categoryName: String): Boolean {
        return categoryName.length !in 6..20
    }

//    private fun checkLetter(categoryName: String) = !isValidStringInput(categoryName)
}