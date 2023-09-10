package com.thechance.core.entity

import com.thechance.core.entity.product.ProductInCart


data class Cart(val products: List<ProductInCart>, val total: Double)

