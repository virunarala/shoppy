package dev.virunarala.shoppy.common.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.virunarala.shoppy.common.data.db.Constants

@Entity(tableName = Constants.TABLE_CART)
data class CartEntity(
    @PrimaryKey
    val id: Long,
    val quantity: Int
)



