package com.example.finalproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.local.dao.GroceryListDao
import com.example.finalproject.data.local.database.GroceryListDatabase
import com.example.finalproject.data.local.entities.GroceryList
import com.example.finalproject.data.local.entities.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class GroceryViewModel(application: Application) : AndroidViewModel(application) {
    val db = GroceryListDatabase.getDatabase(application)
    val groceryListDao = db.groceryListDao()
    val productDao = db.productDao()
    val allLists : Flow<List<GroceryList>> = groceryListDao.getAllLists()

    fun getProductsForList(listId: Int): Flow<List<Product>> {
        return productDao.getProductsForList(listId)
    }

    fun addList(name: String, onListCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val newList = GroceryList(listName = name)
            val newListId = groceryListDao.insertList(newList).toInt()
            onListCreated(newListId)
        }
    }

    fun deleteList(list: GroceryList) {
        viewModelScope.launch {
            groceryListDao.deleteList(list)
        }
    }

    fun addProduct(name: String, listId: Int) {
        viewModelScope.launch {
            productDao.insertProduct(Product(productName = name, listId = listId))
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            productDao.deleteProduct(product)
        }
    }

//    fun toggleProductBought(product: Product) {
//        viewModelScope.launch {
//            productDao.updateProduct(product.copy(isBought = !product.isBought))
//        }
//    }
}