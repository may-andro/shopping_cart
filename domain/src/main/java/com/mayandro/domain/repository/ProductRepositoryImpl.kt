package com.mayandro.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mayandro.data.DataSourceFactory
import com.mayandro.domain.pager.PagerSource
import com.mayandro.domain.pager.ProductPagingSource
import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductItem
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl (
    private val dataSourceFactory: DataSourceFactory,
    private val productPagingSource: PagerSource
) : ProductRepository{

    override fun getAllProducts(pageSize: Int): Flow<PagingData<ProductItem>> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                maxSize = 100,
                enablePlaceholders = false,
                initialLoadSize = pageSize
            )
        ) {
            productPagingSource as ProductPagingSource
        }.flow
    }

    override suspend fun getProductById(id: Int): NetworkStatus<ProductDetail> {
        return dataSourceFactory.retrieveRemoteDataStore().getProductDetailForId(id)
    }
}