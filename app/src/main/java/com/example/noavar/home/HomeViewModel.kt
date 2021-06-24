package com.example.noavar.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noavar.database.ProductDatabase
import com.example.noavar.database.ProductDatabaseDao
import com.example.noavar.model.Product
import com.example.noavar.network.ProductRepository
import com.example.noavar.network.ProductRepositoryImpl
import com.example.noavar.utils.ApiStatus
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel(application: Application):
    AndroidViewModel(application) {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val database = ProductDatabase.getInstance(application).productDatabaseDao

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status

    init {
        getProperties()
    }

    private fun getProperties(){
        coroutineScope.launch {
            val getProperties = repository.getProducts().getProductsAsync()
            try {
                _status.value = ApiStatus.LOADING
                val listResult = getProperties.await()
                _products.value = listResult.result
                insertProducts(listResult.result)
                _status.value = ApiStatus.DONE
            }catch (e: Exception){
                _status.value = ApiStatus.ERROR
                Log.d("HomeViewModel", e.message.toString())
                _products.value = ArrayList()
            }
        }
    }

    private suspend fun insertProducts(products: List<Product>){
        withContext(Dispatchers.IO){
            database.clearAll()
            database.insertAll(products)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun reloading(){
        getProperties()
    }
}