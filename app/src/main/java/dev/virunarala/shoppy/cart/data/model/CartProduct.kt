package dev.virunarala.shoppy.cart.data.model

data class CartProduct(
    val id: Long,
    val name: String,
    val icon: String,
    val price: Double,
    val isFavourite: Boolean,
    val quantity: Int
)
