package com.mayandro.shoppingcart.utils

import com.mayandro.remote.model.ProductItem

fun ProductItem.getImageDimension(): Pair<Int, Int> {
    val width: Int
    val height: Int
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
        }
        else -> {
            width = 0
            height = 0
        }
    }

    return Pair(width, height)
}