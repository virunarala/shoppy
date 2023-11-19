package dev.virunarala.shoppy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.virunarala.shoppy.data.db.Constants

@Entity(tableName = Constants.TABLE_CATEGORY)
data class CategoryEntity(
    @PrimaryKey
    val id: Long,
    val name: String
)




