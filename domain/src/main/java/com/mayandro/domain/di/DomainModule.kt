package com.mayandro.domain.di

import androidx.paging.ExperimentalPagingApi
import com.mayandro.data.DataSourceFactory
import com.mayandro.data.pager.product.ProductRemoteMediator
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.pager.ProductPagingSource
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.repository.ProductRepositoryImpl
import com.mayandro.domain.mapper.UIProductListMapper
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
        dataSourceFactory: DataSourceFactory,
        uiProductListMapper: UIProductListMapper
    ): PagerSource {
        return ProductPagingSource(
            uiProductListMapper = uiProductListMapper,
            dataSourceFactory = dataSourceFactory
        )
    }

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideProductRepository(
        dataSourceFactory: DataSourceFactory,
        productRemoteMediator: ProductRemoteMediator
    ): ProductRepository {
        return ProductRepositoryImpl(
            dataSourceFactory = dataSourceFactory,
            productRemoteMediator = productRemoteMediator
        )
    }


}