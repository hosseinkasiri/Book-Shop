package com.example.noavar.data.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noavar.databinding.ItemListProductBinding
import com.example.noavar.model.Product
import com.example.noavar.utils.ItemClickListener
import com.example.noavar.utils.ProductDiffCallBack

class ListProductAdapter(private val clickListener: ItemClickListener<Product>):
    ListAdapter<Product, ListProductAdapter.ListProductHolder>(ProductDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductHolder {
        return ListProductHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ListProductHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product, clickListener)
    }

    class ListProductHolder private constructor(private val binding: ItemListProductBinding):
        RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product, clickListener: ItemClickListener<Product>){
            binding.product = product
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup): ListProductHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListProductBinding.inflate(layoutInflater, parent, false)
                return ListProductHolder(binding)
            }
        }
    }
}