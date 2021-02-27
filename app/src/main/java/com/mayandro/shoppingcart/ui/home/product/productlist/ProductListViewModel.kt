package com.mayandro.shoppingcart.ui.home.product.productlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.domain.usecase.GetAllProductUseCase
import com.mayandro.shoppingcart.ui.base.BaseViewModel
import com.mayandro.utility.PRODUCT_PAGING_PAGE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val useCase: GetAllProductUseCase
): BaseViewModel<ProductListInteractor>() {

    lateinit var productList: Flow<PagingData<ProductUIItem>>

    init {
        useCase(GetAllProductUseCase.Param(PRODUCT_PAGING_PAGE_SIZE)) {
            viewModelScope.launch {
                productList = it.cachedIn(viewModelScope)
            }
        }
    }

}