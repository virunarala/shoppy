package dev.virunarala.shoppy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.virunarala.shoppy.data.db.Constants

@Entity(tableName = Constants.TABLE_CART)
data class CartEntity(
    @PrimaryKey
    val id: Long,
    val quantity: Int
)



