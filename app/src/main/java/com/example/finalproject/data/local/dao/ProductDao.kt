package com.example.finalproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.data.local.entities.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product WHERE listId = :listId ORDER BY productName ASC")
    fun getProductsForList(listId: Int): Flow<List<Product>>

    @Query("UPDATE product SET isBought = :isBought WHERE productId = :productId")
    suspend fun updateProductStatus(productId: Int, isBought: Boolean)

    @Query("UPDATE product SET price = :price WHERE productId = :productId")
    suspend fun updateProductPrice(productId: Int, price: Double)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product WHERE listId = :listId")
    suspend fun deleteProductsByListId(listId: Int)
}