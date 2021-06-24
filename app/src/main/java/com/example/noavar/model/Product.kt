package com.example.noavar.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey @Json(name = "product_id")
    val id: Int,
    @ColumnInfo @Json(name = "product_title")
    val productTitle: String?,
    @ColumnInfo @Json(name = "product_picture")
    val picture: String?,
    @ColumnInfo @Json(name = "product_regular_price")
    val regularPrice: String?,
    @ColumnInfo @Json(name = "product_sale_price")
    val salePrice: String?,
    @ColumnInfo @Json(name = "product_url")
    val url: String?)