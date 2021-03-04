package com.mayandro.remote

import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductResponse
import com.mayandro.remote.utils.ApiResponseHandler
import com.mayandro.utility.UNKNOWN_NETWORK_EXCEPTION
import com.mayandro.utility.network.NetworkStatus
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@RunWith(JUnit4::class)
class RemoteDataSourceTest {
    @Test
    fun getProductList() {
        val remoteDataSource = mockk<RemoteDataSourceImpl>()

        val page = 1
        val pageSize = 5

        val networkProductResponse = NetworkStatus.Success(ProductResponse(
            list = listOf(),
            page = 0,
            pageSize = pageSize,
            size = 20,
            _link = "",
            _type = "",
            _next = ""
        ))

        //STUB calls
        coEvery { remoteDataSource.getProductList(page, pageSize) } returns networkProductResponse

        //Execute the code
        val result = runBlocking { remoteDataSource.getProductList(page, pageSize) }

        //Verify
        coVerify { remoteDataSource.getProductList(page, pageSize) }

        assertEquals(networkProductResponse, result)
    }

    @Test
    fun getProductDetail() {
        val remoteDataSource = mockk<RemoteDataSourceImpl>()

        val id = 1

        val networkProductDetailResponse =  NetworkStatus.Success(
            ProductDetail(
                image = "",
                id = id,
                price = 2,
                currency = "",
                _link = "",
                _type = "",
                brand = "",
                name = "",
                description = "",
                discountPercentage = 2,
                stock = 4
            )
        )

        //STUB calls
        coEvery { remoteDataSource.getProductDetailForId(id) } returns networkProductDetailResponse

        //Execute the code
        val result = runBlocking { remoteDataSource.getProductDetailForId(id) }

        //Verify
        coVerify { remoteDataSource.getProductDetailForId(id) }

        assertEquals(networkProductDetailResponse, result)
    }

    @Test
    fun testSafeApiCallSuccessfull() {
        mockkObject(ApiResponseHandler)

        val apiRequest = mockk<Response<ProductResponse>>()

        val response = ProductResponse(
            list = listOf(),
            page = 0,
            pageSize = 1,
            size = 20,
            _link = "",
            _type = "",
            _next = ""
        )

        val expectedResponse =  NetworkStatus.Success(response)

        //STUB calls
        every { apiRequest.body() } returns response
        every { apiRequest.isSuccessful } returns true
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        assertEquals(expectedResponse.data, result.data)
    }

    @Test
    fun testSafeApiCallFailed() {
        mockkObject(ApiResponseHandler)

        val apiRequest = mockk<Response<ProductResponse>>()

        val response = ProductResponse(
            list = listOf(),
            page = 0,
            pageSize = 1,
            size = 20,
            _link = "",
            _type = "",
            _next = ""
        )

        val expectedResponse =  NetworkStatus.Error(UNKNOWN_NETWORK_EXCEPTION, data = response)

        //STUB calls
        every { apiRequest.isSuccessful } returns false
        coEvery { ApiResponseHandler.safeApiCall{ apiRequest } } returns expectedResponse

        //Execute the code
        val result = runBlocking { ApiResponseHandler.safeApiCall{ apiRequest } }

        //Verify
        assertEquals(expectedResponse.errorMessage, result.errorMessage)
    }
}