package com.mayandro.domain.usecase

import com.mayandro.domain.repository.ProductRepository
import com.mayandro.remote.model.ProductDetail
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductDetailByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository
): UseCase<GetProductDetailByIdUseCase.Param, NetworkStatus<ProductDetail>>() {

    data class Param(
        val id: Int
    )

    override suspend fun run(param: Param): Flow<NetworkStatus<ProductDetail>> {
        return flow {
            emit(productRepository.getProductById(id = param.id))
        }
    }
}