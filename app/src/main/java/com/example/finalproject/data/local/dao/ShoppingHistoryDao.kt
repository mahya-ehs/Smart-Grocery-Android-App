package com.example.finalproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.data.local.entities.ShoppingHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingHistory(history: ShoppingHistory): Long

    @Query("SELECT * FROM shopping_history ORDER BY shoppingDate DESC")
    fun getAllShoppingHistory(): Flow<List<ShoppingHistory>>

    @Query("SELECT * FROM shopping_history WHERE historyId = :id")
    fun getShoppingHistoryById(id: Int): Flow<ShoppingHistory?>

    @Delete
    fun deleteShoppingHistory(history: ShoppingHistory)

    @Query("UPDATE shopping_history SET receiptPath = :path WHERE historyId = :historyId")
    suspend fun updateReceiptPath(historyId: Int, path: String)

}