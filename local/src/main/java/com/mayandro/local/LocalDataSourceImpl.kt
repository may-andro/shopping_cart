package com.mayandro.local

import com.mayandro.local.dao.ProductDao
import com.mayandro.local.entity.ProductEntity

internal class LocalDataSourceImpl(
    private val productDao: ProductDao
): LocalDataSource {
    override suspend fun insertProductEntity(productEntity: ProductEntity) {
        productDao.insertProduct(productEntity)
    }

    override suspend fun deleteAllProduct() {
        productDao.clearProductTable()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProduct()
    }

    override suspend fun getProductById(id: String): ProductEntity? {
        return productDao.getProductById(id)
    }

}