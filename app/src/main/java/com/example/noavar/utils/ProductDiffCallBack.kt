package com.example.noavar.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.noavar.model.Product

class ProductDiffCallBack: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}