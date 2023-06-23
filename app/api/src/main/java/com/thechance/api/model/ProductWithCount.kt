package com.thechance.api.model

import kotlinx.serialization.Serializable
import java.awt.Image


@Serializable
data class ProductWithCount(
    val id: Long,
    val name: String,
    val count: Int,
    val price: Double,
    val images:List<String>
    )

