package com.example.finalproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject.R
import com.example.finalproject.ui.components.ShoppingContainer
import com.example.finalproject.ui.components.ShoppingHistoryBottom
import com.example.finalproject.ui.constants.background
import com.example.finalproject.ui.constants.darkBlue
import com.example.finalproject.ui.constants.lightGray
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel

@Composable
fun ProductPricesScreen(pagerState: PagerState, viewModel: ShoppingHistoryViewModel = viewModel()) {
    val products by viewModel.productsForList.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()
    val productPrices = remember { mutableStateMapOf<Int, String>() }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.blob),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        ShoppingContainer(
            backgroundImage = R.drawable.blob,
            content = {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    Text("Enter Product Prices", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                    products.forEach { product ->
                        Row(
                            modifier = Modifier
                                .padding(top=10.dp)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(product.productName,
                                modifier = Modifier.weight(1f)
                                    .padding(5.dp),
                                )
                            TextField(
                                value = productPrices[product.productId] ?: "",
                                onValueChange = { newPrice ->
                                    productPrices[product.productId] = newPrice
                                    viewModel.updateProductPrice(product.productId, newPrice.toDoubleOrNull() ?: 0.0)
                                },
                                modifier = Modifier.width(100.dp)
                                    .padding(5.dp).alpha(0.2f),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = lightGray,
                                    focusedContainerColor = background,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    cursorColor = lightGray,
                                    focusedTextColor = darkBlue
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                        }
                    }
                }


            },
            bottomBar = {
                ShoppingHistoryBottom(
                    pagerState = pagerState
                )
            }
        )
    }


}
