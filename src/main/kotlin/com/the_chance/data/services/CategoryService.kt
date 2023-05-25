package com.the_chance.data.services

import com.the_chance.data.models.Category
import com.the_chance.data.tables.CategoryTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class CategoryService(private val database: Database) : BaseService() {

    init {
        transaction(database) {
            SchemaUtils.create(CategoryTable)
        }
    }

    suspend fun create(categoryName: String, categoryImage: String): Category = dbQuery {
        val newCategory = CategoryTable.insert {
            it[name] = categoryName
            it[image] = categoryImage
        }
        Category(
            newCategory[CategoryTable.id].value,
            newCategory[CategoryTable.name],
            newCategory[CategoryTable.image]
        )
    }

    suspend fun getAllCategories(): List<Category> {
        return dbQuery {
            CategoryTable.selectAll().map {
                Category(
                    id = it[CategoryTable.id].value,
                    name = it[CategoryTable.name].toString(),
                    image = it[CategoryTable.image].toString()
                )
            }
        }
    }

    suspend fun remove(categoryId: Int): Boolean = dbQuery {
        CategoryTable.deleteWhere {
            CategoryTable.id eq categoryId
        } > 0
    }

    suspend fun update(categoryId: Int, categoryName: String, categoryImage: String): Boolean = dbQuery {
        CategoryTable.update({ CategoryTable.id eq categoryId }) {
            it[name] = categoryName
            it[image] = categoryImage
        } > 0
    }
}