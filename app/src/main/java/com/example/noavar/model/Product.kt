package com.example.noavar.model

import com.squareup.moshi.Json

data class Product(
    @Json(name = "product_id")
    val id: Int?,
    @Json(name = "product_title")
    val productTitle: String?,
    @Json(name = "product_picture")
    val picture: String?,
    @Json(name = "product_regular_price")
    val regularPrice: String?,
    @Json(name = "product_sale_price")
    val salePrice: String?,
    @Json(name = "product_url")
    val url: String?)