package com.mayandro.shoppingcart.ui.home.product.productlist

import com.mayandro.shoppingcart.databinding.FragmentProductListBinding
import com.mayandro.shoppingcart.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductListViewModel>(), ProductListInteractor {
    override fun getViewModelClass() = ProductListViewModel::class.java

    override fun getViewBinding(): FragmentProductListBinding =
        FragmentProductListBinding.inflate(layoutInflater)

}
