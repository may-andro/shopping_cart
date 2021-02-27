package com.mayandro.domain.usecase

import androidx.paging.PagingData
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.uimodel.ProductUIItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    data class Param(
        val pageSize: Int
    )

    operator fun invoke(
        param: Param,
        onResult: (Flow<PagingData<ProductUIItem>>) -> Unit
    ) {
        onResult(productRepository.getAllProducts(param.pageSize))
    }
}