package com.mayandro.local

import com.mayandro.local.entity.ProductEntity

class FakeLocalDataSource(
    private var products: MutableList<ProductEntity>? = mutableListOf()
): LocalDataSource {
    override suspend fun insertProductEntity(productEntity: ProductEntity) {
        products?.add(productEntity)
    }

    override suspend fun deleteAllProduct() {
        products?.clear()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return products?.toList()?: emptyList()
    }

    override suspend fun getProductById(id: String): ProductEntity? {
        return try {
            products?.find { id == id }
        } catch (e: KotlinNullPointerException) {
            null
        }
    }
}