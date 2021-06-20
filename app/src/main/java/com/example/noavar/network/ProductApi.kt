package com.example.noavar.network

import com.example.noavar.model.NetworkResult
import com.example.noavar.model.Product
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface ProductApi {
    @GET("json.php")
    fun getProductsAsync(): Deferred<NetworkResult<List<Product>>>
}