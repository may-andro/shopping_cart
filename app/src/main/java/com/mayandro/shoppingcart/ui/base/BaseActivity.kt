package com.mayandro.shoppingcart.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding, VM : ViewModel>: AppCompatActivity(){

    lateinit var binding: B

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }

    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    protected abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewBinding(): B
}