package dev.virunarala.shoppy.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.virunarala.shoppy.data.model.CartEntity
import dev.virunarala.shoppy.data.model.CategoryEntity
import dev.virunarala.shoppy.data.model.ProductEntity

@Database(entities = [ProductEntity::class,CategoryEntity::class,CartEntity::class], version = 1)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
}