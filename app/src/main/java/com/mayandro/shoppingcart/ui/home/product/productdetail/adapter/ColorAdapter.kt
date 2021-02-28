package com.mayandro.shoppingcart.ui.home.product.productdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.ItemProductColorBinding
import com.mayandro.shoppingcart.utils.AutoUpdatableAdapter
import kotlin.properties.Delegates

class ColorAdapter() : RecyclerView.Adapter<ColorAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var dataSet: List<ItemColorModel> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new ) { o, n ->
            o == n
        }
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_product_color, viewGroup,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    interface OnItemClickListener {
        fun onDropDownClick(item: ItemColorModel, position: Int)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductColorBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.onDropDownClick(dataSet[absoluteAdapterPosition], absoluteAdapterPosition)
            }
        }

        fun bind(data: ItemColorModel) {
            binding.cardColor.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, data.color))
        }
    }
}


data class ItemColorModel(
    val color: Int,
    val id: Int
)