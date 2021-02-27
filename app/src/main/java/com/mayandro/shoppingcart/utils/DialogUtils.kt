package com.mayandro.shoppingcart.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mayandro.shoppingcart.databinding.BottomSheetGeneralNotificationBinding


class DialogUtils {
    var bottomSheetDialog: BottomSheetDialog? = null

    fun showAlertMessage(
        context: Context,
        title: String,
        message: String,
        positiveButton: String,
        negativeButton: String = "",
        positiveButtonClickListener: ((View) -> Unit)? = null,
        negativeButtonClickListener: ((View) -> Unit)? = null,
        isCancellable: Boolean = true
    ) {

        dismissDialog()

        val binding = BottomSheetGeneralNotificationBinding.inflate(LayoutInflater.from(context))

        binding.textViewDialogTitle.text = title
        if (message.isEmpty()) {
            binding.textViewDialogMessage.visibility = View.GONE
        }
        binding.textViewDialogMessage.text = message

        binding.buttonPositive.apply {
            text = positiveButton
            if (positiveButton.isNotEmpty()) this.visibility = View.VISIBLE
            setOnClickListener {
                dismissDialog()
                positiveButtonClickListener?.invoke(it)
            }
        }

        binding.buttonNegative.apply {
            text = negativeButton
            if (negativeButton.isNotEmpty()) this.visibility = View.VISIBLE
            setOnClickListener {
                dismissDialog()
                negativeButtonClickListener?.invoke(it)
            }
        }

        bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog?.setContentView(binding.root)
        bottomSheetDialog?.setCanceledOnTouchOutside(false)
        bottomSheetDialog?.setCancelable(isCancellable)
        val bottomSheet = bottomSheetDialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.let {
            val bottomSheetBehavior = BottomSheetBehavior.from(it)
            bottomSheetDialog?.setOnShowListener {
                bottomSheetBehavior.setPeekHeight(binding.root.height)
            }
        }
        bottomSheetDialog?.show()
    }


    fun dismissDialog(): Boolean {
        bottomSheetDialog?.let {
            if (it.isShowing) {
                it.dismiss()
                return true
            }
        }
        return false
    }

}