package com.mayandro.shoppingcart.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.utils.DialogUtils

abstract class BaseFragment<B : ViewBinding, VM : ViewModel>() : Fragment(){

    lateinit var binding: B

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }

    private val dialogUtils: DialogUtils by lazy { DialogUtils() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        return binding.root
    }

    protected abstract fun getViewModelClass(): Class<VM>

    abstract fun getViewBinding(): B

    fun clearDialogMessage() {
        dialogUtils.dismissDialog()
    }

    fun showDialogMessage(
        title: String,
        message: String,
        positiveButton: String = getString(R.string.close),
        negativeButton: String = "",
        positiveButtonClickListener: ((View) -> Unit)? = null,
        negativeButtonClickListener: ((View) -> Unit)? = null,
        isCancellable: Boolean = true
    ) {
        dialogUtils.showAlertMessage(
            title = title,
            message = message,
            positiveButton = positiveButton,
            positiveButtonClickListener = positiveButtonClickListener,
            negativeButton = negativeButton,
            negativeButtonClickListener = negativeButtonClickListener,
            isCancellable = isCancellable,
            context = requireContext()
        )
    }

}