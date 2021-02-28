package com.mayandro.local.di

import android.content.Context
import androidx.room.Room
import com.mayandro.local.LocalDataSource
import com.mayandro.local.LocalDataSourceImpl
import com.mayandro.local.dao.ProductDao
import com.mayandro.local.dao.RemoteKeysDao
import com.mayandro.local.database.ProductDatabase
import com.mayandro.local.utils.DbConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ProductDatabase = Room.databaseBuilder(
        context,
        ProductDatabase::class.java,
        DATABASE_NAME)
        .fallbackToDestructiveMigration() // allows database to be cleared after upgrading version
        .build()

    @Singleton
    @Provides
    fun provideProductDao(db: ProductDatabase): ProductDao = db.productDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(db: ProductDatabase): RemoteKeysDao = db.remoteKeysDao()


    @Singleton
    @Provides
    fun provideLocalDataSource(
        productDao: ProductDao,
        remoteKeysDao: RemoteKeysDao
    ): LocalDataSource {
        return LocalDataSourceImpl(
            productDao, remoteKeysDao
        )
    }
}