package com.mayandro.shoppingcart.ui.home.product.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mayandro.domain.usecase.GetProductDetailByIdUseCase
import com.mayandro.remote.model.ProductDetail
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.ui.base.BaseViewModel
import com.mayandro.shoppingcart.ui.home.product.productdetail.adapter.ItemColorModel
import com.mayandro.shoppingcart.ui.home.product.productdetail.adapter.ItemSizeModel
import com.mayandro.utility.network.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailByIdUseCase: GetProductDetailByIdUseCase
): BaseViewModel<ProductDetailInteractor>() {

    var productDetailLiveData: MutableLiveData<NetworkStatus<ProductDetail>> = MutableLiveData()

    init {
        productDetailLiveData.postValue(NetworkStatus.Loading())
    }

    fun getProductDetail(productId: Int) {
        getProductDetailByIdUseCase(
            scope = viewModelScope,
            param = GetProductDetailByIdUseCase.Param(productId),
            onResult = {
                productDetailLiveData.postValue(it)
            },
            onFailure = {
                productDetailLiveData.postValue(NetworkStatus.Error("Something went wrong"))
            }
        )
    }

    fun getSizeList(): List<ItemSizeModel>{
        return listOf(
            ItemSizeModel(
                label = "S",
                id = 1
            ),
            ItemSizeModel(
                label = "M",
                id = 2
            ),
            ItemSizeModel(
                label = "L",
                id = 3
            ),
            ItemSizeModel(
                label = "XL",
                id = 4
            ),
        )
    }

    fun getColorList(): List<ItemColorModel>{
        return listOf(
            ItemColorModel(
                color = R.color.red,
                id = 1
            ),
            ItemColorModel(
                color = R.color.green,
                id = 2
            ),
            ItemColorModel(
                color = R.color.blue,
                id = 3
            ),
            ItemColorModel(
                color = R.color.black,
                id = 4
            ),
            ItemColorModel(
                color = R.color.orange,
                id = 5
            ),
            ItemColorModel(
                color = R.color.grey,
                id = 6
            )
        )
    }
}