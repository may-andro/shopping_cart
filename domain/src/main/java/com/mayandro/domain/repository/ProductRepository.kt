package com.mayandro.domain.repository

import androidx.paging.PagingData
import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductItem
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(pageSize: Int): Flow<PagingData<ProductItem>>

    suspend fun getProductById(
        id: Int
    ): NetworkStatus<ProductDetail>
}