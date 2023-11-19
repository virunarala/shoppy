package dev.virunarala.shoppy.home.data.model

import dev.virunarala.shoppy.data.model.CategoryEntity

data class Product(
    val id: Long,
    val name: String,
    val icon: String,
    val price: Double,
    val isFavourite: Boolean,
    val category: CategoryEntity?
)
