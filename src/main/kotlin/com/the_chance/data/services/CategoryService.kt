package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoriesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryService(private val database: Database) : BaseService() {

    init {
        transaction(database) {
            SchemaUtils.create(CategoriesTable)
        }
    }

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

    suspend fun remove(categoryId: Int): Boolean = dbQuery {
        CategoriesTable.update({ CategoriesTable.id eq categoryId }) {
            it[isDeleted] = true
        } > 0
    }

    suspend fun update(categoryId: Int, categoryName: String, categoryImage: String): Boolean = dbQuery {
        CategoriesTable.update({ CategoriesTable.id eq categoryId }) {
            if (categoryName.isNotEmpty()){
                it[name] = categoryName
            }

            if(categoryImage.isNotEmpty()){
                it[image] = categoryImage
            }

        } > 0
    }
}