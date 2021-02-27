package com.mayandro.remote

import com.mayandro.remote.model.ProductDetail
import com.mayandro.remote.model.ProductItem
import com.mayandro.remote.model.ProductResponse
import com.mayandro.utility.network.NetworkStatus

class FakeRemoteDataSource: RemoteDataSource {

    private var shouldReturnNetworkError = false

    private val fakeProductResponse = ProductResponse(
        list = listOf(
            ProductItem(
                image = "",
                id = 1,
                price = 2,
                currency = "",
                _link = "",
                _type = "",
                brand = "",
                name = ""
            )
        ),
        pageSize = 5,
        page = 1,
        size = 20,
        _link = "",
        _type = "",
        _next = ""
    )

    override suspend fun getProductList(page: Int, pageSize: Int): NetworkStatus<ProductResponse> {
        return if(shouldReturnNetworkError)
            NetworkStatus.Success(fakeProductResponse)
        else
            NetworkStatus.Error("Network Issue")
    }

    override suspend fun getProductDetailForId(id: Int): NetworkStatus<ProductDetail> {
        return if(shouldReturnNetworkError) {
            NetworkStatus.Success(
                ProductDetail(
                    image = "",
                    id = id,
                    price = 2,
                    currency = "",
                    _link = "",
                    _type = "",
                    brand = "",
                    name = "",
                    description = "",
                    discountPercentage = 2,
                    stock = 4
                )
            )
        } else {
            NetworkStatus.Error("Network Issue")
        }
    }
}