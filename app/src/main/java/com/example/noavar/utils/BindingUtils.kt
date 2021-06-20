package com.example.noavar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?){
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

@BindingAdapter("setText")
fun bindText(textView: TextView, string: String?){
    textView.text = string
}