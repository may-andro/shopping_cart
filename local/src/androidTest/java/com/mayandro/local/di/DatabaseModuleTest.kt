package com.mayandro.local.di

import android.content.Context
import androidx.room.Room
import com.mayandro.local.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModuleTest {
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(
            context,
            ProductDatabase::class.java
        ).allowMainThreadQueries()
            .build()

}