package com.mayandro.domain

import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetProductDetailByIdUseCaseTest {

    private val productRepository: ProductRepository = mockk()

    private lateinit var getProductDetailByIdUseCase: GetProductDetailByIdUseCase

    @Before
    fun setUp() {
        getProductDetailByIdUseCase = GetProductDetailByIdUseCase(
            productRepository
        )
    }

    @InternalCoroutinesApi
    @Test
    fun testGetProductDetailByIdUseCaseTest() {
        runBlocking {
            val param = GetProductDetailByIdUseCase.Param(
                id = 1
            )

            val productdetail = ProductDetail(
                id = 1,
                name = "Test",
                image = "Test",
                currency = "Test",
                price = 1,
                stock = 1,
                _link = "",
                _type = "",
                description = "1",
                brand = "2",
                discountPercentage = 1
            )

            val serverResponse = NetworkStatus.Success(productdetail)

            //STUB calls
            coEvery { productRepository.getProductById(1) } returns serverResponse

            //Execute the code
            val flow = getProductDetailByIdUseCase.run(param)

            //Verify
            val result = flow.single()
            assertEquals(result, serverResponse)
        }
    }
}