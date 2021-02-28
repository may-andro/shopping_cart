package com.mayandro.domain


import androidx.paging.ExperimentalPagingApi
import com.mayandro.data.DataSourceFactory
import com.mayandro.data.pager.product.ProductRemoteMediator
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.repository.ProductRepositoryImpl
import com.mayandro.remote.RemoteDataSource
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {
    private val remoteDataSource: RemoteDataSource = mock()
    private val dataSourceFactory: DataSourceFactory = mock()
    @ExperimentalPagingApi
    private val productRemoteMediator: ProductRemoteMediator = mock()

    private lateinit var productRepository: ProductRepository

    @ExperimentalPagingApi
    @Before
    fun setUp() {
        productRepository = ProductRepositoryImpl(
            productRemoteMediator = productRemoteMediator,
            dataSourceFactory = dataSourceFactory
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
        whenever(remoteDataSource.getProductDetailForId(1)).thenReturn(mockServerData)
        whenever(dataSourceFactory.retrieveRemoteDataStore()).thenReturn(remoteDataSource)

        val getProductDetail = productRepository.getProductById(1)
        assertEquals(getProductDetail, mockServerData)

        verify(dataSourceFactory.retrieveRemoteDataStore(),
            com.nhaarman.mockitokotlin2.atLeastOnce()
        ).getProductDetailForId(1)
        return@runBlocking
    }
}

