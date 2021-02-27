package com.mayandro.domain.mapper

import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.remote.model.ProductItem
import com.mayandro.remote.model.ProductResponse
import javax.inject.Inject

class UIProductListMapper @Inject constructor(): ObjectMapper<ProductResponse, List<ProductUIItem>>() {
    override fun mapFromOriginalObject(originalObject: ProductResponse): List<ProductUIItem> {
        return originalObject.list.toProductUIList()
    }

}

fun List<ProductItem>.toProductUIList(): List<ProductUIItem> {
    return this.map {
        it.toProductUIItem()
    }

}


fun ProductItem.toProductUIItem(): ProductUIItem {
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
    return ProductUIItem(
        id,
        name,
        brand,
        price,
        currency,
        image,
        _link,
        _type,
        0,
        width,
        height
    )
}
