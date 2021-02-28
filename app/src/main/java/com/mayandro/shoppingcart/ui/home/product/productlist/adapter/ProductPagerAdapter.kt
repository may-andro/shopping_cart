package com.mayandro.shoppingcart.ui.home.product.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mayandro.domain.uimodel.ProductUIItem
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.ItemProductsBinding
import com.mayandro.utility.extensions.setImage

class ProductPagerAdapter: PagingDataAdapter<ProductUIItem, ProductPagerAdapter.ViewHolder>(ProductComparator) {
    private val set = ConstraintSet()
    private val requestOptions = RequestOptions()

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: ProductUIItem, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        getItem(position)?.let {
            viewHolder.bind(it)
        }
    }

    inner class ViewHolder(private val binding: ItemProductsBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cardImageHolder.setOnClickListener { _ ->
                getItem(absoluteAdapterPosition)?.let {
                    onItemClickListener?.onItemClick( it , absoluteAdapterPosition)
                }
            }
        }

        fun bind(data: ProductUIItem) {
            binding.cardTitle.text = data.brand
            binding.cardSubtitle.text = "${data.price} ${data.currency}"

            binding.cardImage.requestLayout()

            binding.cardImage.setImage(
                data.image,
                requestOptions
            )

            if(data.width > 0 && data.height > 0) {
                with(set) {
                    val posterRatio = String.format("%d:%d", data.width, data.height)
                    clone(binding.parentContsraint)
                    setDimensionRatio(binding.cardImageHolder.id, posterRatio)
                    applyTo(binding.parentContsraint)
                }
            }
        }
    }

    object ProductComparator : DiffUtil.ItemCallback<ProductUIItem>() {
        override fun areItemsTheSame(oldItem: ProductUIItem, newItem: ProductUIItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUIItem, newItem: ProductUIItem): Boolean {
            return oldItem == newItem
        }
    }
}
