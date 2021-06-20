package com.example.noavar.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ProductRepositoryImpl: ProductRepository {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor{ chain ->
        val url = chain
            .request()
            .url
            .newBuilder()
            .build()
        chain.proceed(chain.request().newBuilder().url(url).build())
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    private val retrofitClient: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://noavarpub.com/interview/android/"
    }

    override fun getProducts(): ProductApi {
        return retrofitClient
    }
}