package com.mayandro.domain

import com.mayandro.domain.mapper.UIProductDetailMapper
import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.remote.model.ProductDetail
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class UIProductDetailMapperTest {

    private lateinit var uiProductDetailMapper: UIProductDetailMapper

    @Before
    fun setUp() {
        uiProductDetailMapper = UIProductDetailMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val productDetail = ProductDetail(
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
        val productDetailUIItem = ProductDetailUIItem(
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

        assertEquals(productDetail.name, productDetailUIItem.name)
        assertEquals(productDetail.id, productDetailUIItem.id)
    }

}