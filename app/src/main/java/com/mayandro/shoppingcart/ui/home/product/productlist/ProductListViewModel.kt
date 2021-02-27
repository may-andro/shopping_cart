package com.mayandro.shoppingcart.ui.home.product.productlist


import com.mayandro.domain.repository.ProductRepository
import com.mayandro.shoppingcart.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository
): BaseViewModel<ProductListInteractor>() {

}