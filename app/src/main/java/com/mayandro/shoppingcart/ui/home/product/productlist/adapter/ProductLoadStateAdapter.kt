package com.mayandro.shoppingcart.ui.home.product.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.core.view.isVisible
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.shoppingcart.databinding.ItemLoadStateBinding

class ProductLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ProductLoadStateAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bindData(loadState)
    }

    inner class ViewHolder(private val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bindData(loadState: LoadState) {
            binding.apply {
                loadProgress.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                loadMessage.isVisible = loadState !is LoadState.Loading
            }

            if (loadState is LoadState.Error){
                binding.loadMessage.text = loadState.error.localizedMessage
            }


        }
    }

}