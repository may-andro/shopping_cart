package com.mayandro.local

import androidx.paging.PagingSource
import com.mayandro.local.entity.ProductEntity
import com.mayandro.local.entity.RemoteKeys
import java.lang.Exception

class FakeLocalDataSource(
    private var products: MutableList<ProductEntity>? = mutableListOf(),
    private var remotekeys: MutableList<RemoteKeys>? = mutableListOf()
): LocalDataSource {
    override suspend fun insertProductEntity(productEntity: List<ProductEntity>) {
        products?.addAll(productEntity)
    }

    override suspend fun deleteAllProduct() {
        products?.clear()
    }

    override suspend fun getAllProducts(): List<ProductEntity> {
        return products?.toList()?: emptyList()
    }

    override fun getAllPagedProduct(): PagingSource<Int, ProductEntity> {
        throw Exception()
    }

    override suspend fun getProductById(id: String): ProductEntity? {
        return try {
            products?.find { id == id }
        } catch (e: KotlinNullPointerException) {
            null
        }
    }

    override suspend fun insertRemoteKeyEntity(list: List<RemoteKeys>) {
        remotekeys?.addAll(list)
    }

    override suspend fun deleteAllRemoteKeys() {
        remotekeys?.clear()
    }

    override suspend fun getRemoteKeysById(id: String): RemoteKeys? {
        return try {
            remotekeys?.find { id == id }
        } catch (e: KotlinNullPointerException) {
            null
        }
    }

    override suspend fun getLastRemoteKeys(): RemoteKeys? {
        return remotekeys?.last()
    }
}