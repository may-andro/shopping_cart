package com.mayandro.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.usecase.GetAllProductUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

class GetAllProductUseCaseTest {
    private val productRepository: ProductRepository = mock()

    private lateinit var getAllProductUseCase: GetAllProductUseCase

    @Before
    fun setUp() {
        getAllProductUseCase = GetAllProductUseCase(
            productRepository
        )
    }

    @Test
    fun getProductList() {
        val param = GetAllProductUseCase.Param(
            pageSize = 1
        )
        val config = PagingConfig(
            pageSize = 5,
            initialLoadSize = 5
        )
        val pager = Pager(config, 0) {
            MockPagingSource()
        }
        val mockServerData = pager.flow

        whenever(productRepository.getAllProducts(1)).thenReturn(mockServerData)
        getAllProductUseCase.invoke(param, {})

        verify(productRepository).getAllProducts(1)
        verifyNoMoreInteractions(productRepository)
    }
}