package com.example.noavar.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.noavar.R

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

@BindingAdapter("ApiStatus")
fun bindStatus(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}