package com.recyclerview.using.kotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.using.kotlin.R
import com.recyclerview.using.kotlin.model.Product

class ProductRecyclerViewAdapter(private val context: Context, private var items: ArrayList<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ItemViewHolder>() {

    private var itemClickListener: ItemClickListener? = null
    private var itemLongClickListener: ItemLongClickListener? = null

    fun add(items: ArrayList<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun replace(items: ArrayList<Product>) {
        items.run {
            clear()
            add(items)
        }
        notifyDataSetChanged()
    }

    fun add(position: Int, product: Product) {
        items.add(position, product)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        if (items.size > 0) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_row_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        private val productImageView: ImageView = itemView.findViewById(R.id.productImageView)
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productPriceTextView: TextView =
            itemView.findViewById(R.id.productPriceTextView)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bindItem(items: Product) {
            productNameTextView.text = items.name
            productPriceTextView.text = items.price.toString()
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (itemClickListener != null && position != RecyclerView.NO_POSITION) {
                itemClickListener?.onClick(position, view, items[position])
            }
        }

        override fun onLongClick(view: View?): Boolean {
            val position = adapterPosition
            if (itemLongClickListener != null && position != RecyclerView.NO_POSITION) {
                itemLongClickListener?.onLongClick(position, view, items[position])
                return true
            }
            return false
        }
    }

    interface ItemClickListener {
        fun onClick(position: Int, view: View?, item: Product)
    }

    interface ItemLongClickListener {
        fun onLongClick(position: Int, view: View?, item: Product)
    }

    fun setItemClickListener(listener: ItemClickListener) {
        this.itemClickListener = listener
    }

    fun setItemLongClickListener(listener: ItemLongClickListener) {
        this.itemLongClickListener = listener
    }
}