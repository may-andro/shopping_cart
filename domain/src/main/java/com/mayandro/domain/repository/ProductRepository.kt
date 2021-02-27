package com.mayandro.domain.repository

import androidx.paging.PagingData
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(pageSize: Int): Flow<PagingData<ProductUIItem>>

    suspend fun getProductById(
        id: Int
    ): NetworkStatus<ProductDetail>
}