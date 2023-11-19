package dev.virunarala.shoppy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dev.virunarala.shoppy.data.model.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM ${Constants.TABLE_CART}")
    fun getAll(): Flow<List<CartEntity>>

    @Query("SELECT SUM(${CartTableColumns.QUANTITY}) FROM ${Constants.TABLE_CART}")
    fun getCount(): Flow<Int>

    @Query("SELECT * FROM ${Constants.TABLE_CART} WHERE ${CartTableColumns.ID} = :id")
    suspend fun findById(id: Long): List<CartEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartEntity: CartEntity)

    @Query("DELETE FROM ${Constants.TABLE_CART} WHERE ${CartTableColumns.ID} = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM ${Constants.TABLE_CART}")
    suspend fun deleteAll()

    @Transaction
    suspend fun addToCart(id: Long) {
        val cartEntities = findById(id)
        val quantity = if(cartEntities.isEmpty()) 1 else cartEntities.first().quantity + 1
        insert(CartEntity(id,quantity))
    }



    @Query("UPDATE ${Constants.TABLE_CART} SET ${CartTableColumns.QUANTITY} = ${CartTableColumns.QUANTITY} - 1 WHERE ${CartTableColumns.ID} = :id")
    suspend fun decreaseQuantity(id: Long)

    @Query("UPDATE ${Constants.TABLE_CART} SET ${CartTableColumns.QUANTITY} = ${CartTableColumns.QUANTITY} + 1 WHERE ${CartTableColumns.ID} = :id")
    suspend fun increaseQuantity(id: Long)
}


