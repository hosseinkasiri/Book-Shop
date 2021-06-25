package com.example.noavar.scene.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noavar.data.database.ProductDatabase
import com.example.noavar.model.Product
import com.example.noavar.data.network.ProductRepository
import com.example.noavar.data.network.ProductRepositoryImpl
import com.example.noavar.utils.ApiStatus
import kotlinx.coroutines.*
import java.lang.Exception

class HomeViewModel(application: Application):
    AndroidViewModel(application) {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main+ job)
    private val repository: ProductRepository = ProductRepositoryImpl()
    private val database = ProductDatabase.getInstance(application).productDatabaseDao

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> get() = _status
    private val _isEmptyDatabase = MutableLiveData<Boolean>()
    val isEmptyDatabase: LiveData<Boolean> get() = _isEmptyDatabase

    init {
        initializeDatabaseEmpty()
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

    private suspend fun getProductsFromDatabase(): List<Product>{
        return withContext(Dispatchers.IO){
            database.getAllProducts()
        }
    }

    private suspend fun isEmpty(): Boolean{
        return withContext(Dispatchers.IO){
            database.getAllProducts().isEmpty()
        }
    }

    private fun initializeDatabaseEmpty(){
         coroutineScope.launch {
             _isEmptyDatabase.value = isEmpty()
        }
    }

    fun reloading(){
        getProperties()
    }

    fun offlineMode(){
        coroutineScope.launch {
            _products.value = getProductsFromDatabase()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}