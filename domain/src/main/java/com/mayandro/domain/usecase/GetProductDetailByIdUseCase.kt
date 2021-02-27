package com.mayandro.domain.usecase

import com.mayandro.domain.mapper.UIProductDetailMapper
import com.mayandro.domain.repository.ProductRepository
import com.mayandro.domain.uimodel.ProductDetailUIItem
import com.mayandro.utility.exceptions.NetworkFailureException
import com.mayandro.utility.network.NetworkStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetProductDetailByIdUseCase @Inject constructor(
    private val productRepository: ProductRepository,
    private val uiProductDetailMapper: UIProductDetailMapper
): UseCase<GetProductDetailByIdUseCase.Param, ProductDetailUIItem>() {

    data class Param(
        val id: Int
    )

    override suspend fun run(param: Param): Flow<ProductDetailUIItem> {
        return when(val response = productRepository.getProductById(id = param.id)) {
            is NetworkStatus.Success -> {
                response.data ?.let {
                    flow {
                        emit(uiProductDetailMapper.mapFromOriginalObject(it))
                    }
                }?: kotlin.run {
                    throw Exception("Empty response")
                }
            }
            is NetworkStatus.Error -> {
                throw NetworkFailureException(response.errorMessage?: "Unexpected Error")
            }
            else -> {
                throw NetworkFailureException("No status found for Network")
            }
        }
    }
}