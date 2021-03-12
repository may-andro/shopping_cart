package com.mayandro.domain.di

import com.mayandro.data.DataSourceFactory
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.pager.ProductPagingSource
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    @Singleton
    fun provideProductPagingSource(
        dataSourceFactory: DataSourceFactory
    ): PagerSource {
        return ProductPagingSource(
            dataSourceFactory = dataSourceFactory
        )
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        dataSourceFactory: DataSourceFactory,
        pagingSource: ProductPagingSource
    ): ProductRepository {
        return ProductRepositoryImpl(
            dataSourceFactory = dataSourceFactory,
            productPagingSource = pagingSource
        )
    }


}