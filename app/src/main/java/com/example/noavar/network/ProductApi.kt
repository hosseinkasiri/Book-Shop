package com.example.noavar.network

import com.example.noavar.model.Product
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ProductApi {
    @GET
    fun getProductsAsync(): Deferred<List<Product>>
}