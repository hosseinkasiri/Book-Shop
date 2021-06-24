package com.example.noavar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noavar.model.Product

@Dao
interface ProductDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(products: List<Product?>?)

    @Query("DELETE FROM product_table")
    fun clearAll()

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProducts(): List<Product>
}