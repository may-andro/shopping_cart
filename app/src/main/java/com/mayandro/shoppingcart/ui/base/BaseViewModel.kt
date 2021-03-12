package com.mayandro.shoppingcart.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancelChildren

abstract class BaseViewModel<VI: ViewInteractor>: ViewModel() {

    var viewInteractor: VI? = null
        set

    override fun onCleared() {
        viewModelScope.coroutineContext.cancelChildren()
        viewInteractor =null
        super.onCleared()
    }
}