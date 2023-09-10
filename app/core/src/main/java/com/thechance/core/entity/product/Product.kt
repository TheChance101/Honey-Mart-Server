package com.thechance.core.entity.product

import com.thechance.core.entity.Image

data class Product(
    val id: Long,
    val name: String,
    val quantity: String?,
    val price: Double,
    val image: List<Image>,
    val marketId: Long
)
