package dev.virunarala.shoppy.common.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.virunarala.shoppy.common.data.db.Constants
import dev.virunarala.shoppy.common.data.db.ProductTableColumns

@Entity(tableName = Constants.TABLE_PRODUCT)
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val icon: String,
    val price: Double,
    @ColumnInfo(name = ProductTableColumns.IS_FAVOURITE) val isFavourite: Boolean,
    @ColumnInfo(name = ProductTableColumns.CATEGORY_ID) val categoryId: Long
)