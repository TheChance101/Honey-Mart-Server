package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoriesTable
import org.jetbrains.exposed.sql.*

class CategoryService(
    private val database: Database
) : BaseService(database, CategoriesTable) {

    suspend fun create(categoryName: String, categoryImage: String): Category = dbQuery {
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

    suspend fun remove(categoryId: Int?): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()
        val isCategoryDeleted = CategoriesTable.select { CategoriesTable.isDeleted eq false }.singleOrNull()

        if (category != null && isCategoryDeleted != null) {
            CategoriesTable.update({ CategoriesTable.isDeleted eq false }) {
                it[isDeleted] = true
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }

    suspend fun update(categoryId: Int?, categoryName: String, categoryImage: String): Boolean = dbQuery {
        val category = CategoriesTable.select { CategoriesTable.id eq categoryId }.singleOrNull()

        if (category != null) {
            CategoriesTable.update({ CategoriesTable.id eq categoryId }) { categoryRow ->
                if (categoryName.isNotEmpty()) {
                    categoryRow[name] = categoryName
                }
                if (categoryImage.isNotEmpty()) {
                    categoryRow[image] = categoryImage
                }
            } > 0
        } else {
            throw NoSuchElementException("This category id $categoryId not found.")
        }
    }
}