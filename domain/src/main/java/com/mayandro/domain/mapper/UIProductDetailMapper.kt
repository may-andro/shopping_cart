package com.mayandro.domain.mapper

import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.remote.model.ProductDetail
import javax.inject.Inject

class UIProductDetailMapper @Inject constructor(): ObjectMapper<ProductDetail, ProductDetailUIItem>()  {
    override fun mapFromOriginalObject(originalObject: ProductDetail): ProductDetailUIItem {
        return originalObject.toProductDetailUIItem()
    }
}

fun ProductDetail.toProductDetailUIItem(): ProductDetailUIItem {
    return ProductDetailUIItem(
        id = id,
        name = name,
        brand = brand,
        price = price,
        currency = currency,
        image = image,
        link = _link,
        type = _type,
        cardBackground = 0,
        width = 0,
        height = 0,
        description = description,
        discountPercentage = discountPercentage,
        stock = stock
    )
}