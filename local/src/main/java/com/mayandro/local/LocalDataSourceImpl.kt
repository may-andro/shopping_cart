package com.mayandro.local

import androidx.paging.PagingSource
import com.mayandro.local.dao.ProductDao
import com.mayandro.local.dao.RemoteKeysDao
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.entity.RemoteKeys

class LocalDataSourceImpl(
    private val productDao: ProductDao,
    private val remoteKeysDao: RemoteKeysDao
): LocalDataSource {
    override suspend fun insertProductEntity(productEntity: List<ProductEntity>) {
        productDao.insertProduct(productEntity)
    }

    override suspend fun deleteAllProduct() {
        productDao.clearProductTable()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return productDao.getAllProduct()
    }

    override fun getAllPagedProduct(): PagingSource<Int, ProductEntity> {
        return productDao.getAllPagedProduct()
    }

    override suspend fun getProductById(id: String): ProductEntity? {
        return productDao.getProductById(id)
    }

    override suspend fun insertRemoteKeyEntity(remoteKeys: List<RemoteKeys>) {
        remoteKeysDao.insertAll(remoteKey = remoteKeys)
    }

    override suspend fun deleteAllRemoteKeys() {
        remoteKeysDao.clearRemoteKeys()
    }

    override suspend fun getRemoteKeysById(id: String): RemoteKeys? {
        return remoteKeysDao.getRemoteKeyById(id)
    }

    override suspend fun getLastRemoteKeys(): RemoteKeys? {
        return remoteKeysDao.getLastSavedRemoteKey()
    }

}