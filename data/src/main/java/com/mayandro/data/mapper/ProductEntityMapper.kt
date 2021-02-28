package com.mayandro.data.mapper

import com.mayandro.local.entity.ProductEntity
import com.mayandro.remote.model.ProductItem
import com.mayandro.remote.model.ProductResponse
import com.mayandro.utility.mapper.ObjectMapper
import javax.inject.Inject

class ProductEntityMapper @Inject constructor(): ObjectMapper<ProductResponse, List<ProductEntity>>() {
    override fun mapFromOriginalObject(originalObject: ProductResponse): List<ProductEntity> {
        return originalObject.list.toProductEntityList()
    }

}

fun List<ProductItem>.toProductEntityList(): List<ProductEntity> {
    return this.map {
        it.toProductEntity()
    }

}

fun ProductItem.toProductEntity(): ProductEntity {
    val width: Int
    val height: Int
    when (this.name) {
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
    return ProductEntity(
        id,
        name,
        brand,
        price,
        currency,
        image,
        _link,
        _type,
        height,
        width
    )
}
