package com.example.finalproject.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery_list")
data class GroceryList(
    @PrimaryKey(autoGenerate = true) val listId: Int = 0,
    val listName: String,
    val isBought: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
