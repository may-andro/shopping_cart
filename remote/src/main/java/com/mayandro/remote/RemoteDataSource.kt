package com.mayandro.remote

import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import com.mayandro.remote.model.ProductResponse


interface RemoteDataSource {
    suspend fun getProductList(
        page: Int,
        pageSize: Int
    ): NetworkStatus<ProductResponse>

    suspend fun getProductDetailForId(
        id: Int
    ): NetworkStatus<ProductDetail>
}