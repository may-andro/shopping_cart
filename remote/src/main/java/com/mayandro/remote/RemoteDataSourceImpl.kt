package com.mayandro.remote

import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import com.mayandro.remote.model.ProductResponse
import com.mayandro.remote.retrofit.RetrofitApi
import com.mayandro.remote.utils.ApiResponseHandler.safeApiCall

internal class RemoteDataSourceImpl(
    private val retrofit: RetrofitApi
): RemoteDataSource {
    override suspend fun getProductList(
        page: Int,
        pageSize: Int
    ): NetworkStatus<ProductResponse> {
        return safeApiCall {
            retrofit.getProductList(
                "ddf49ca9-44cf-4613-b218-ddc030bbfa63",
                page,
                pageSize
            )
        }
    }

    override suspend fun getProductDetailForId(id: Int): NetworkStatus<ProductDetail> {
        return safeApiCall {
            retrofit.getProductDetailById(
                "ddf49ca9-44cf-4613-b218-ddc030bbfa63",
                productId = id
            )
        }
    }
}