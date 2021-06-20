package com.example.noavar.utils

class ItemClickListener<T>(val onClickListener: (item: T) -> Unit){
    fun onClick(item: T) = onClickListener(item)
}