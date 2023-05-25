package com.the_chance.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object CategoriesTable : IntIdTable() {
    val name = text("name")
    val image = text("image")
    val isDeleted = bool("isDeleted")
}