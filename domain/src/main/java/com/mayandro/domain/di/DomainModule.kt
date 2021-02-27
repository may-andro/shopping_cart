package com.mayandro.domain.di

import com.mayandro.data.DataSourceFactory
import com.mayandro.domain.mapper.ObjectMapper
import com.mayandro.domain.mapper.UIProductDetailMapper
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.pager.ProductPagingSource
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.repository.ProductRepositoryImpl
import com.mayandro.domain.mapper.UIProductListMapper
import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductResponse
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    @Provides
    @Singleton
    @Named("UIProductDetailMapper")
    fun provideUIProductDetailMapper(): ObjectMapper<ProductDetail, ProductDetailUIItem> {
        return UIProductDetailMapper()
    }

    @Provides
    @Singleton
    @Named("UIProductListMapper")
    fun provideUIProductListMapper(): ObjectMapper<ProductResponse, List<ProductUIItem>> {
        return UIProductListMapper()
    }

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