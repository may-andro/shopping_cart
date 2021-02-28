package com.mayandro.domain

import com.mayandro.domain.mapper.ObjectMapper
import com.mayandro.domain.mapper.UIProductDetailMapper
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetProductDetailByIdUseCaseTest {

    private val productRepository: ProductRepository = mock()

    private lateinit var getProductDetailByIdUseCase: GetProductDetailByIdUseCase

    @Before
    fun setUp() {
        getProductDetailByIdUseCase = GetProductDetailByIdUseCase(
            productRepository
        )
    }

    @Test
    fun getProductDetails() {
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

        val uiproductdetail = ProductDetailUIItem(
            id = 1,
            name = "Test",
            image = "Test",
            currency = "Test",
            price = 1,
            stock = 1,
            description = "1",
            brand = "2",
            discountPercentage = 1,
            width = 1,
            height = 1,
            cardBackground = 1,
            link = "",
            type = ""
        )

        val serverResponse = NetworkStatus.Success(productdetail)

        runBlocking {
            whenever(productRepository.getProductById(1)).thenReturn(serverResponse)

            getProductDetailByIdUseCase.run(param)
            verify(productRepository).getProductById(1)
        }

        verifyNoMoreInteractions(productRepository)
    }
}