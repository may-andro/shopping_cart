package com.mayandro.shoppingcart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.ui.home.product.productdetail.ProductDetailViewModel
import com.mayandro.utility.network.NetworkStatus
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.*
import com.mayandro.domain.repository.ProductRepositoryImpl

class ProductDetailViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = mockk<ProductRepositoryImpl>()
    private val getProductDetailByIdUseCase = spyk(GetProductDetailByIdUseCase(repository))

    private lateinit var viewModel: ProductDetailViewModel

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        viewModel = ProductDetailViewModel(getProductDetailByIdUseCase)
    }

    private fun createProductDetailObserver(): Observer<NetworkStatus<ProductDetail>> = spyk(Observer { })

    @ExperimentalCoroutinesApi
    @Test
    fun fetchProductTest() {
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
        val successResource = NetworkStatus.Success(productDetail)
        val validParam = GetProductDetailByIdUseCase.Param(1)
        val resultFlow = flow { emit(successResource) }

        val mockedObserver = createProductDetailObserver()
        viewModel.productDetailLiveData.observeForever(mockedObserver)

        //STUB calls
        coEvery { repository.getProductById(validParam.id) } returns successResource
        coEvery { getProductDetailByIdUseCase.run(validParam) } returns resultFlow
        //Execute the code
        viewModel.getProductDetail(validParam.id)
        //Verify
        //verify { mockedObserver.onChanged(successResource) }
        val status = viewModel.productDetailLiveData.getOrAwaitValue(time = 30)
        assertEquals(status, successResource)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        unmockkAll()
    }
}