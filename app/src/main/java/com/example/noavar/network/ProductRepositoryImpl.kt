package com.example.noavar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ProductRepositoryImpl: ProductRepository {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    private val retrofitClient: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://noavarpub.com/interview/android/json.php"
    }

    override fun getProducts(): ProductApi {
        return retrofitClient
    }
}