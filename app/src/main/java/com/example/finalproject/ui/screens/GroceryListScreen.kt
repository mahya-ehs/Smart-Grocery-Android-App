package com.example.finalproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.ui.components.GroceryListContainer
import com.example.finalproject.ui.components.GroceryListHeader
import com.example.finalproject.ui.components.GroceryListInputBar
import com.example.finalproject.ui.constants.ListInputBarHeight
import com.example.finalproject.viewmodel.GroceryViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun GroceryListScreen(
    navController: NavController,
    listId: Int,
    viewModel: GroceryViewModel = viewModel()
) {
    val products by viewModel.getProductsForList(listId).collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.blob_haikei),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            GroceryListHeader(taskCount = products.size)
            GroceryListContainer(
                productsFlow = flowOf(products),
                onItemDelete = { product -> viewModel.deleteProduct(product) },
                overlappingElementsHeight = ListInputBarHeight
            )
        }
        GroceryListInputBar(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onAddButtonClick = { name -> viewModel.addProduct(name, listId) }
        )
    }
}
