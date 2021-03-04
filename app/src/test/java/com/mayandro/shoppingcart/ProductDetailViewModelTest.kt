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
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import kotlinx.coroutines.test.resetMain
import org.junit.*

class ProductDetailViewModelTest {

    private lateinit var viewModel: ProductDetailViewModel

    private val getProductDetailByIdUseCase = mockk<GetProductDetailByIdUseCase>()

    private lateinit var productDetailObserver: Observer<NetworkStatus<ProductDetail>>

    private val productDetail = ProductDetail(
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
    private val successResource = NetworkStatus.Success(productDetail)
    private val validParam = GetProductDetailByIdUseCase.Param(1)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        productDetailObserver = mockk()
        viewModel = ProductDetailViewModel(getProductDetailByIdUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun fetchProductTest() = runBlocking {
        coEvery { getProductDetailByIdUseCase.run(validParam) } returns  (flow { emit(successResource) })

        viewModel.productDetailLiveData.observeForever {
            assertEquals(it, successResource)
        }

        viewModel.getProductDetail(validParam.id)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }
}