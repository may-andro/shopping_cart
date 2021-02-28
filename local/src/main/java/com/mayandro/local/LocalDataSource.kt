package com.mayandro.local

import androidx.paging.PagingSource
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.entity.RemoteKeys

interface LocalDataSource {
    suspend fun insertProductEntity(productEntity: List<ProductEntity>)

    suspend fun deleteAllProduct()

    suspend fun getAllProducts(): List<ProductEntity>

    fun getAllPagedProduct(): PagingSource<Int, ProductEntity>

    suspend fun getProductById(id: String): ProductEntity?

    suspend fun insertRemoteKeyEntity(remoteKeys: List<RemoteKeys>)

    suspend fun deleteAllRemoteKeys()

    suspend fun getRemoteKeysById(id: String): RemoteKeys?

    suspend fun getLastRemoteKeys(): RemoteKeys?
}