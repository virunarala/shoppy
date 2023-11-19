package dev.virunarala.shoppy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.virunarala.shoppy.data.model.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT}")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT} WHERE ${ProductTableColumns.ID} = :id")
    fun findById(id: Long): Flow<ProductEntity>

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT}  WHERE ${ProductTableColumns.ID} IN (:ids)")
    fun findAllByIds(ids: LongArray): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT}  WHERE ${ProductTableColumns.CATEGORY_ID} = :categoryId")
    fun findByCategory(categoryId: Long): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ${Constants.TABLE_PRODUCT}  WHERE ${ProductTableColumns.IS_FAVOURITE} = 1")
    fun getFavourites(): Flow<List<ProductEntity>>

    @Query("UPDATE ${Constants.TABLE_PRODUCT} SET ${ProductTableColumns.IS_FAVOURITE} = :isFavourite WHERE ${ProductTableColumns.ID} = :id")
    fun setFavourite(id: Long, isFavourite: Int)

    @Insert
    suspend fun insertAll(vararg products: ProductEntity)
}

