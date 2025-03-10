package com.example.finalproject.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_history")
data class ShoppingHistory(
    @PrimaryKey(autoGenerate = true) val historyId: Int = 0,
    val listId: Int,
    val shoppingDate: Long = System.currentTimeMillis(),
    val storeName: String,
    val totalPrice: Double = 0.0,
    val receiptPath: String? = null
)