package com.mayandro.domain

import com.mayandro.data.DataSourceFactory
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.repository.ProductRepositoryImpl
import com.mayandro.remote.RemoteDataSource
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {
    private val remoteDataSource: RemoteDataSource = mockk()
    private val dataSourceFactory: DataSourceFactory = mockk()

    private val productPagingSource =  MockPagingSource()

    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        productRepository = ProductRepositoryImpl(
            dataSourceFactory,
            productPagingSource
        )
    }

    private fun mockProductDetailNetworkResponse() = NetworkStatus.Success(
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

    @Test
    fun getProductDetail() = runBlocking {
        val mockServerData = mockProductDetailNetworkResponse()

        //STUB calls
        coEvery { remoteDataSource.getProductDetailForId(1) } returns mockServerData
        coEvery { dataSourceFactory.retrieveRemoteDataStore() } returns remoteDataSource

        //Execute the code
        val getProductDetail = productRepository.getProductById(1)

        //Verify
        coVerify { dataSourceFactory.retrieveRemoteDataStore() }

        assertEquals(getProductDetail, mockServerData)
    }
}

