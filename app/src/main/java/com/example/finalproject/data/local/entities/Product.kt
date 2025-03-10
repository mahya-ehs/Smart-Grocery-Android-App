package com.example.finalproject.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product",
    foreignKeys = [ForeignKey(
        entity = GroceryList::class,
        parentColumns = ["listId"],
        childColumns = ["listId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Product(
    @PrimaryKey(autoGenerate = true) val productId: Int = 0,
    val listId: Int,
    val productName: String,
    val isBought: Boolean = false,
    val price: Double = 0.0
)
