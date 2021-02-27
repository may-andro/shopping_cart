package com.mayandro.shoppingcart.ui.home

import android.os.Bundle
import com.mayandro.shoppingcart.databinding.ActivityHomeBinding
import com.mayandro.shoppingcart.ui.base.BaseActivity
import com.mayandro.utility.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: BaseActivity<ActivityHomeBinding, HomeViewModel>(), HomeViewInteractor {

    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.viewInteractor = this
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun showErrorMessage(error: String) {
        this.showToast(error)
    }
}