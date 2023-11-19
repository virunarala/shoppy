package dev.virunarala.shoppy.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): ShopDatabase {
        return Room.databaseBuilder(
            context,
            ShopDatabase::class.java,
            Constants.DB_NAME
        )
            .createFromAsset("shop.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(shopDb: ShopDatabase): ProductDao {
        return shopDb.productDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(shopDb: ShopDatabase): CategoryDao {
        return shopDb.categoryDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(shopDb: ShopDatabase): CartDao {
        return shopDb.cartDao()
    }
}