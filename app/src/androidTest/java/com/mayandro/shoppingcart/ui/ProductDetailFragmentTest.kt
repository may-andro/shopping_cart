package com.mayandro.shoppingcart.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.filters.MediumTest
import com.mayandro.shoppingcart.ui.home.product.productdetail.ProductDetailFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.mayandro.domain.repository.ProductRepositoryImpl
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.launchFragmentInHiltContainer
import com.mayandro.utility.network.NetworkStatus
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProductDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val productRepository: ProductRepositoryImpl = mock()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun isProgressVisible() {
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
        runBlocking {
            whenever(productRepository.getProductById(1)).doReturn(NetworkStatus.Success(productDetail))
        }
        launchFragmentInHiltContainer<ProductDetailFragment>(fragmentArgs = bundleOf("productId" to 1))
        onView(withId(R.id.buttonCart)).perform(ViewActions.click())
    }
}