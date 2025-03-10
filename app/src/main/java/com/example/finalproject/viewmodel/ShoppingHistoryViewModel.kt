package com.example.finalproject.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.data.local.database.GroceryListDatabase
import com.example.finalproject.data.local.entities.Product
import com.example.finalproject.data.local.entities.ShoppingHistory
import com.example.finalproject.ui.screens.saveImageToInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ShoppingHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = GroceryListDatabase.getDatabase(application)
    private val shoppingHistoryDao = db.shoppingHistoryDao()
    private val productDao = db.productDao()

    val allHistory: Flow<List<ShoppingHistory>> = shoppingHistoryDao.getAllShoppingHistory()

    private val _selectedListId = MutableStateFlow<Int?>(null)
    val selectedListId: StateFlow<Int?> = _selectedListId

    private val _productPrices = MutableStateFlow<Map<Int, Double>>(emptyMap())
    val productPrices: StateFlow<Map<Int, Double>> = _productPrices

    private val _selectedStore = MutableStateFlow<String?>(null)
    val selectedStore: StateFlow<String?> = _selectedStore

    private val _receiptImagePath = MutableStateFlow<String?>(null)
    val receiptImagePath: StateFlow<String?> = _receiptImagePath

    fun setReceiptImagePath(path: String) {
        _receiptImagePath.value = path
    }

    private val _productsForList = _selectedListId.flatMapLatest { listId ->
        if (listId != null) {
            productDao.getProductsForList(listId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val productsForList: StateFlow<List<Product>> = _productsForList

    fun setSelectedListId(id: Int) {
        if (_selectedListId.value != id) {
            _selectedListId.value = id
            _productPrices.value = emptyMap()
        }
    }

    fun updateProductPrice(productId: Int, price: Double) {
        _productPrices.value = _productPrices.value.toMutableMap().apply {
            this[productId] = price
        }
    }

    fun setSelectedStore(storeName: String) {
        _selectedStore.value = storeName
    }

    fun submitShoppingHistory() {
        viewModelScope.launch {
            val listId = _selectedListId.value ?: return@launch
            val storeName = _selectedStore.value ?: return@launch
            val prices = _productPrices.value
            val totalPrice = prices.values.sum()

            val history = ShoppingHistory(
                listId = listId,
                storeName = storeName,
                totalPrice = totalPrice,
                shoppingDate = System.currentTimeMillis(),
                receiptPath = ""
            )
            val historyId = shoppingHistoryDao.insertShoppingHistory(history)

            prices.forEach { (productId, price) ->
                productDao.updateProductPrice(productId, price)
            }

            db.groceryListDao().markListAsBought(listId)

            _productPrices.value = emptyMap()
            _selectedListId.value = null
            _selectedStore.value = null
        }
    }

    fun deleteShoppingHistory(shoppingHistory: ShoppingHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            shoppingHistoryDao.deleteShoppingHistory(shoppingHistory)
        }
    }

    fun getShoppingHistory(historyId: Int): Flow<ShoppingHistory?> {
        return shoppingHistoryDao.getShoppingHistoryById(historyId)
    }

    fun getProductsForList(listId: Int): Flow<List<Product>> {
        return productDao.getProductsForList(listId)
    }

    fun attachReceiptToHistory(historyId: Int, uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val imagePath = saveImageToInternalStorage(context, uri)
            shoppingHistoryDao.updateReceiptPath(historyId, imagePath)
        }
    }




}
