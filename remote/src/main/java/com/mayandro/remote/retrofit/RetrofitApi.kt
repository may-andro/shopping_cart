package com.mayandro.remote.retrofit

import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {

    @GET("/products")
    suspend fun getProductList(
        @Header("Authorization") header: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ) : Response<ProductResponse>

    @GET("/products/{productId}")
    suspend fun getProductDetailById(
        @Header("Authorization") header: String,
        @Path("productId") productId: Int
    ) : Response<ProductDetail>
}