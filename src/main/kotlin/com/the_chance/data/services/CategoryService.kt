package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.services.validation.CategoryValidation
import com.the_chance.data.tables.CategoriesTable
import com.the_chance.utils.toLowerCase
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.select

class CategoryService(
    private val database: Database
) : BaseService(database, CategoriesTable) {

    private val categoryValidation by lazy { CategoryValidation() }

    suspend fun create(categoryName: String, categoryImage: String): Category = dbQuery {
        val categoryList = CategoriesTable.select {
            CategoriesTable.name.lowerCase() eq  categoryName.toLowerCase()
        }.singleOrNull()
        val errors = categoryValidation.checkCreateValidation(categoryName, categoryImage, categoryList)


        if (errors.isEmpty()) {
            val newCategory = CategoriesTable.insert {
                it[name] = categoryName
                it[image] = categoryImage
                it[isDeleted] = false
            }
            Category(
                id = newCategory[CategoriesTable.id].value,
                name = newCategory[CategoriesTable.name].toString(),
                image = newCategory[CategoriesTable.image].toString(),
            )
        } else {
            throw Throwable(errors.joinToString { it })
        }
    }

    suspend fun getAllCategories(): List<Category> {
        return dbQuery {
            CategoriesTable.select { CategoriesTable.isDeleted eq false }.map { resultRow ->
                Category(
                    id = resultRow[CategoriesTable.id].value,
                    name = resultRow[CategoriesTable.name].toString(),
                    image = resultRow[CategoriesTable.image].toString(),
                )
            }
        }
    }

    suspend fun remove(categoryId: Long): Boolean = dbQuery {
        val category = CategoriesTable.select {
            (CategoriesTable.id eq categoryId) and (CategoriesTable.isDeleted eq false)
        }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                it[isDeleted] = true
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    suspend fun update(categoryId: Long, categoryName: String, categoryImage: String): Boolean = dbQuery {
        val checkCategoryId = CategoriesTable.select { (CategoriesTable.id eq categoryId) }.singleOrNull()
        val checkCategoryName = CategoriesTable.select { (CategoriesTable.name.lowerCase() eq categoryName.toLowerCase()) }.singleOrNull()

        if (checkCategoryId != null) {
            if (checkCategoryName == null) {
                CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
                    if (categoryName.isNotEmpty()) {
                        categoryRow[name] = categoryName
                    }
                    if (categoryImage.isNotEmpty()) {
                        categoryRow[image] = categoryImage
                    }
                } > 0
            } else {
                throw NoSuchElementException("This name already exist")
            }
        } else {
            throw NoSuchElementException("is already updated")
        }
    }
}