package com.recyclerview.using.kotlin.adapter

import android.R.attr.data
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.recyclerview.using.kotlin.R
import com.recyclerview.using.kotlin.model.Product


class ProductRecyclerViewAdapter(val context: Context, var items: ArrayList<Product>) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ItemViewHolder>() , Filterable {

    private var filterItems: ArrayList<Product> = items

    private var itemClickListener: ItemClickListener? = null
    private var itemLongClickListener: ItemLongClickListener? = null

    @SuppressLint("NotifyDataSetChanged")
    fun add(items: ArrayList<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
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
        private val productPriceTextView: TextView = itemView.findViewById(R.id.productPriceTextView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun bindItem(items: Product) {
            productNameTextView.text = items.name
            productPriceTextView.text = items.price.toString()
            checkBox.isChecked = items.isSelected
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

    @SuppressLint("NotifyDataSetChanged")
    fun checkCheckBox(position: Int, item: Product) {
        items[position] = item
        notifyDataSetChanged()
        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(charsequence: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if(charsequence == null || charsequence.length < 0){
                    filterResults.count = filterItems.size
                    filterResults.values = filterItems
                }else{
                    val searchCharSequence = charsequence.toString().lowercase()
                    val itemModal = ArrayList<Product>()

                    for(item in filterItems){
                        if(item.name.lowercase().contains(searchCharSequence) || item.name!!.lowercase().contains(searchCharSequence)){
                            itemModal.add(item)
                        }
                    }

                    filterResults.count = itemModal.size
                    filterResults.values = itemModal
                }
                return filterResults;
            }

            override fun publishResults(charsequence: CharSequence?, filterResults: FilterResults?) {
                items = filterResults?.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }
}