package com.thechance.core.entity.product

import com.thechance.core.entity.Image

data class ProductInCart(
    val id: Long,
    val name: String,
    val count: Int,
    val price: Double,
    val images:List<Image>
    )

