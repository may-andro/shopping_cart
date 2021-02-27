package com.mayandro.local

import com.mayandro.local.entity.ProductEntity

interface LocalDataSource {
    suspend fun insertProductEntity(productEntity: ProductEntity)

    suspend fun deleteAllProduct()

    suspend fun getAllProducts(): List<ProductEntity>

    suspend fun getProductById(id: String): ProductEntity?
}