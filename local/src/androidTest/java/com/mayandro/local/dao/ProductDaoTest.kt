package com.mayandro.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.mayandro.local.database.ProductDatabase
import com.mayandro.local.entity.ProductEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class ProductDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: ProductDatabase

    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        hiltRule.inject()
        productDao = database.productDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertUser() = runBlockingTest {
        val product = ProductEntity(
            id = "123",
            name = "Clint",
            brand = "Brand",
            currency = "Euro",
            price = 1,
            image = "",
            _link = "",
            _type = ""
        )
        productDao.insertProduct(product)
        val allProducts = productDao.getAllProduct()
        assertThat(allProducts).contains(product)

    }
}