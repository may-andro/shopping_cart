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
import com.mayandro.local.entity.ProductEntity
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.ItemProductsBinding

class ProductPagerAdapter: PagingDataAdapter<ProductEntity, ProductPagerAdapter.ViewHolder>(ProductComparator) {
    private val set = ConstraintSet()
    private val requestOptions = RequestOptions().placeholder(R.drawable.ic_baseline_image_24)

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(item: ProductEntity, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            holder.bind(item!!)
        } else {
            onBindViewHolder(holder, position)
        }
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

        fun bind(data: ProductEntity) {
            binding.cardTitle.text = data.brand
            binding.cardSubtitle.text = "${data.price} ${data.currency}"

            binding.cardImage.requestLayout()

            Glide.with(binding.cardImage.context)
                .setDefaultRequestOptions(requestOptions)
                .load(data.image)
                .fitCenter()
                .thumbnail(0.3f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.cardImage)

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

    object ProductComparator : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }
    }
}
