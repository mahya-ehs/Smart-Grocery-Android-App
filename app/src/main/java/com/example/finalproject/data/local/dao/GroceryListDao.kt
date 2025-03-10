package com.example.finalproject.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject.data.local.entities.GroceryList
import kotlinx.coroutines.flow.Flow


@Dao
interface GroceryListDao {
    @Query("SELECT * FROM grocery_list")
    fun getAllLists(): Flow<List<GroceryList>>

    @Query("SELECT * FROM grocery_list WHERE listId = :listId")
    suspend fun getListById(listId: Int): GroceryList?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(groceryList: GroceryList): Long

    @Query("UPDATE grocery_list SET isBought = 1 WHERE listId = :id")
    suspend fun markListAsBought(id: Int)

    @Delete
    suspend fun deleteList(groceryList: GroceryList)
}