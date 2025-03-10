package com.example.finalproject.data.local.database;

import androidx.room.Database;
import androidx.room.Room;

import android.content.Context;
import androidx.room.RoomDatabase
import com.example.finalproject.data.local.dao.GroceryListDao;
import com.example.finalproject.data.local.dao.ProductDao;
import com.example.finalproject.data.local.dao.ShoppingHistoryDao
import kotlin.jvm.Volatile;
import com.example.finalproject.data.local.entities.*;

@Database(entities = [GroceryList::class, Product::class, ShoppingHistory::class], version = 1, exportSchema = false)
abstract class GroceryListDatabase : RoomDatabase() {

    abstract fun groceryListDao(): GroceryListDao
    abstract fun productDao(): ProductDao
    abstract fun shoppingHistoryDao(): ShoppingHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryListDatabase? = null

        fun getDatabase(context:Context): GroceryListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryListDatabase::class.java,
                    "grocery_database"
                )
                    .setJournalMode(RoomDatabase.JournalMode.AUTOMATIC)
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
