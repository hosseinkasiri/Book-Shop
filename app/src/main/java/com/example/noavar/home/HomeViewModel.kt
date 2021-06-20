package com.example.noavar.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.noavar.model.Product
import com.example.noavar.network.ProductRepository
import com.example.noavar.network.ProductRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)
    private val repository: ProductRepository = ProductRepositoryImpl()

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    init {
        getProperties()
    }

    private fun getProperties(){
        coroutineScope.launch {
            val getProperties = repository.getProducts().getProductsAsync()
            try {
                val listResult = getProperties.await()
                _products.value = listResult.result
            }catch (e: Exception){
                Log.d("HomeViewModel", e.message.toString())
                _products.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}