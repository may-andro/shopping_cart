package com.mayandro.shoppingcart.ui.home.product.productdetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mayandro.shoppingcart.R
import com.mayandro.shoppingcart.databinding.ItemProductSizeBinding
import com.mayandro.shoppingcart.utils.AutoUpdatableAdapter
import kotlin.properties.Delegates

class SizeAdapter() : RecyclerView.Adapter<SizeAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var dataSet: List<ItemSizeModel> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new ) { o, n ->
            o == n
        }
    }

    private var onItemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.item_product_size, viewGroup,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onDropDownClick(item: ItemSizeModel, position: Int)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductSizeBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                onItemClickListener?.onDropDownClick(dataSet[absoluteAdapterPosition], absoluteAdapterPosition)
            }
        }

        fun bind(data: ItemSizeModel) {
            binding.textTitle.text = data.label
        }
    }
}

data class ItemSizeModel(
    val label: String,
    val id: Int
)
