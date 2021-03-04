package com.mayandro.shoppingcart.ui

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.util.HumanReadables
import androidx.test.filters.MediumTest
import com.mayandro.shoppingcart.ui.home.product.productdetail.ProductDetailFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.launchFragmentInHiltContainer
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher
import java.util.concurrent.TimeoutException
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ProductDetailFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var getProductDetailByIdUseCase: GetProductDetailByIdUseCase

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

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testClickButton() {
        runBlocking {
            getProductDetailByIdUseCase.run(GetProductDetailByIdUseCase.Param(1))
        }

        launchFragmentInHiltContainer<ProductDetailFragment>(fragmentArgs = bundleOf("productId" to 1))


        onView(withId(R.id.progressBar)).check(ViewAssertions.matches((isDisplayed())))

        onView(withId(R.id.progressBar)).perform(waitUntilGone(30000L))

        onView(withId(R.id.motionLayout)).check(ViewAssertions.matches((isDisplayed())))

        onView(allOf(withId(R.id.buttonCart), withParent(withId(R.id.motionLayout)))).perform(ViewActions.click())
    }

    private fun waitUntilGone(timeout: Long): ViewAction {
        return WaitUntilGoneAction(timeout)
    }
}

class WaitUntilGoneAction(private val timeout: Long) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return any(View::class.java)
    }

    override fun getDescription(): String {
        return "wait up to $timeout milliseconds for the view to be gone"
    }

    override fun perform(uiController: UiController, view: View) {

        val endTime = System.currentTimeMillis() + timeout

        do {
            if (view.visibility == View.GONE) return
            uiController.loopMainThreadForAtLeast(50)
        } while (System.currentTimeMillis() < endTime)

        throw PerformException.Builder()
            .withActionDescription(description)
            .withCause(TimeoutException("Waited $timeout milliseconds"))
            .withViewDescription(HumanReadables.describe(view))
            .build()
    }
}