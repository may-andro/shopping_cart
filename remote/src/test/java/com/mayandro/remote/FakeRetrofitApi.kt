package com.mayandro.remote

import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductResponse
import com.mayandro.remote.retrofit.RetrofitApi
import retrofit2.Response

class FakeRetrofitApi: RetrofitApi {
    override suspend fun getProductList(
        header: String,
        page: Int,
        pageSize: Int
    ): Response<ProductResponse> {
        return Response.success(
            ProductResponse(
                list = listOf(),
                page = page,
                pageSize = pageSize,
                size = 20,
                _link = "",
                _type = "",
                _next = ""
            )
        )
    }

    override suspend fun getProductDetailById(
        header: String,
        productId: Int
    ): Response<ProductDetail> {
        return Response.success(
            ProductDetail(
                id = 0,
                name = "test",
                description = "test",
                brand = "",
                price = 2,
                currency = "",
                discountPercentage = 2,
                image = "",
                stock = 2,
                _link = "",
                _type = ""
            )
        )
    }
}