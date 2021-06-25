package com.example.noavar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.noavar.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?){
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.default_picture)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)
        .dontAnimate()
        .dontTransform()
    Glide.with(imageView.context)
        .load(url)
        .apply(options)
        .into(imageView)
}

@BindingAdapter("setText")
fun bindText(textView: TextView, string: String?){
    textView.text = string
}

@BindingAdapter("priceProduct")
fun bindPrice(textView: TextView, price: String?){
    var array = ArrayList<Char>()
    val charArray = price!!.toCharArray()
    var number = 1
    for (i in charArray.size-1 downTo 0){
        if (number%3 == 0 && number != 1 && number!=charArray.size){
            array.add(charArray[i])
            array.add(',')
            number++
            continue
        }
        array.add(charArray[i])
        number++
    }
    array.reverse()
    textView.text = array.joinToString("") + " " + "تومان"
}