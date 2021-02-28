package com.mayandro.domain.repository

import androidx.paging.*
import com.mayandro.data.DataSourceFactory
import com.mayandro.data.pager.product.ProductRemoteMediator
import com.mayandro.local.entity.ProductEntity
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl @ExperimentalPagingApi constructor(
    private val dataSourceFactory: DataSourceFactory,
    private val productRemoteMediator: ProductRemoteMediator
) : ProductRepository{

    @ExperimentalPagingApi
    override fun getAllProducts(pageSize: Int): Flow<PagingData<ProductEntity>> {
        val pageConfig = PagingConfig(
            pageSize = pageSize,
            maxSize = 100,
            enablePlaceholders = false,
            initialLoadSize = pageSize,
        )

        return Pager(
            config = pageConfig,
            remoteMediator = productRemoteMediator
        ){
            dataSourceFactory.retrieveLocalDataStore().getAllPagedProduct()
        }.flow
    }

    override suspend fun getProductById(id: Int): NetworkStatus<ProductDetail> {
        return dataSourceFactory.retrieveRemoteDataStore().getProductDetailForId(id)
    }
}
