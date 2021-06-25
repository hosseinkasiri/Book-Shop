package com.example.noavar.data.network

interface ProductRepository {
    fun getProducts(): ProductApi
}