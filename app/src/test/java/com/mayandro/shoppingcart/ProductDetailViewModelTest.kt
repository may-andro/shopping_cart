package com.mayandro.shoppingcart

import com.mayandro.domain.repository.ProductRepositoryImpl
import org.junit.Before
import org.junit.Test
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.ui.home.product.productdetail.ProductDetailViewModel
import com.mayandro.utility.network.NetworkStatus
import io.mockk.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After

class ProductDetailViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel

    private val getProductDetailByIdUseCase = mockk<GetProductDetailByIdUseCase>()
    private val productRepository = mockk<ProductRepositoryImpl>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
       viewModel = ProductDetailViewModel(
           getProductDetailByIdUseCase
       )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchProductTest() = runBlockingTest {
        val productDetail = ProductDetail(
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

        val networkResponse = NetworkStatus.Success(productDetail)

        coEvery { productRepository.getProductById(1) } returns networkResponse

        viewModel.getProductDetail(1)

        val value = viewModel.productDetailLiveData.getOrAwaitValue()
        assertThat(value.data == (networkResponse.data))
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}