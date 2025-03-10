package com.example.finalproject.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.local.entities.Product
import com.example.finalproject.ui.constants.MediumDp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun GroceryListContainer(
    modifier: Modifier = Modifier,
    productsFlow: Flow<List<Product>> = flowOf(listOf()),
    onItemClick: (Product) -> Unit = {},
    onItemDelete: (Product) -> Unit = {},
    overlappingElementsHeight: Dp = 0.dp
) {
    val products = productsFlow.collectAsState(initial = listOf()).value
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MediumDp),
        verticalArrangement = Arrangement.spacedBy(MediumDp)
    ) {
        items(products, key = { it.productId }) { item ->
            GroceryListItemUi(
                listItem = item,
                onItemClick = onItemClick,
                onItemDelete = onItemDelete
            )
        }
        item { Spacer(modifier = Modifier.height(overlappingElementsHeight)) }
    }
}