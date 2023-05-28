package com.the_chance.data.services.validation

import com.the_chance.data.models.Category
import com.the_chance.utils.isValidStringInput

class CategoryValidation {

    fun checkCreateValidation(categoryName: String, categoryImage: String, categoryList: List<Category>): List<String> {
        val errorList = mutableListOf<String>()

        when {
            categoryList.isNotEmpty() -> errorList.add("This category with name $categoryName already exist.")
            categoryImage.isEmpty() -> errorList.add("All field is required...")
            checkNameLength(categoryName) -> errorList.add("Category name should be more than 4 and shorter than 20 characters...")
            checkLetter(categoryName) -> errorList.add("category name should contain letters without numbers or symbols.")
        }
        return errorList
    }

    private fun checkNameLength(categoryName: String): Boolean {
        return categoryName.length !in 6..20
    }

    private fun checkLetter(categoryName: String) = !isValidStringInput(categoryName)
}