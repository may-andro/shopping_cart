package com.mayandro.shoppingcart.ui.home

import com.mayandro.shoppingcart.ui.base.ViewInteractor

interface HomeViewInteractor: ViewInteractor {
    fun showErrorMessage(error: String)
}