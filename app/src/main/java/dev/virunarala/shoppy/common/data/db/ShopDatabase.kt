package dev.virunarala.shoppy.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.virunarala.shoppy.common.data.model.CartEntity
import dev.virunarala.shoppy.common.data.model.CategoryEntity
import dev.virunarala.shoppy.common.data.model.ProductEntity

@Database(entities = [ProductEntity::class, CategoryEntity::class, CartEntity::class], version = 1)
abstract class ShopDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun cartDao(): CartDao
}