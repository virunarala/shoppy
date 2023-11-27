package dev.virunarala.shoppy.common.data.db

import androidx.room.Dao
import androidx.room.Query
import dev.virunarala.shoppy.common.data.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM ${Constants.TABLE_CATEGORY}")
    fun getAll(): Flow<List<CategoryEntity>>
}