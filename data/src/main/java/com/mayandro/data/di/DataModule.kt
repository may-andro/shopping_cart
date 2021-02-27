package com.mayandro.data.di

import com.mayandro.data.DataSourceFactory
import com.mayandro.data.DataSourceFactoryImpl
import com.mayandro.local.LocalDataSource
import com.mayandro.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Singleton
    @Provides
    fun provideDataSource(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): DataSourceFactory = DataSourceFactoryImpl(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )

}