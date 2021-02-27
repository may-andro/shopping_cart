package com.mayandro.domain.utils

import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductItem


fun ProductDetail.toProductDetailUIItem(): ProductDetailUIItem {
    var width: Int
    var height: Int
    when(this.name) {
        "shoes" -> {
            width = 770
            height = 882
        }
        "jeans" -> {
            width = 970
            height = 1182
        }
        "tshirts" -> {
            width = 620
            height = 757
        }
        "shirts" -> {
            width = 620
            height = 757
        }
        "hats" -> {
            width = 400
            height = 400
        } else -> {
        width = 0
        height = 0
    }
    }
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
        width = width,
        height = height,
        description = description,
        discountPercentage = discountPercentage,
        stock = stock
    )
}

