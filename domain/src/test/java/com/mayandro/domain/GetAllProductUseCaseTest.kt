package com.mayandro.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.usecase.GetAllProductUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class GetAllProductUseCaseTest {
    private val productRepository: ProductRepository = mockk()

    private lateinit var getAllProductUseCase: GetAllProductUseCase

    @Before
    fun setUp() {
        getAllProductUseCase = GetAllProductUseCase(
            productRepository
        )
    }

    @Test
    fun testGetAllProductUseCaseTest() {
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

        //STUB calls
        every { productRepository.getAllProducts(1) } returns mockServerData

        //Execute the code
        val result = getAllProductUseCase.invoke(param, {})

        //Verify
        verify { productRepository.getAllProducts(1) }
    }
}