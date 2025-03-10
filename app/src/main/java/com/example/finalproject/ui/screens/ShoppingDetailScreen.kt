package com.example.finalproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel
import kotlinx.coroutines.flow.flowOf
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.example.finalproject.R
import com.example.finalproject.ui.components.CustomButton
import com.example.finalproject.ui.constants.lightBlue
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ShoppingDetailScreen(
    navController: NavController,
    historyId: Int,
    viewModel: ShoppingHistoryViewModel = viewModel()
) {
    val history by viewModel.getShoppingHistory(historyId).collectAsState(initial = null)

    val products by remember(history?.listId) {
        history?.listId?.let { viewModel.getProductsForList(it) }
            ?: flowOf(emptyList())
    }.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.haikei1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            history?.let { shoppingHistory ->
                Text("Store: ${shoppingHistory.storeName}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(
                    "Date: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(shoppingHistory.shoppingDate))}",
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Products", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                LazyColumn {
                    items(products) { product ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(product.productName, modifier = Modifier.weight(1f))
                            Text("$${product.price}", fontWeight = FontWeight.Bold)
                        }
                    }
                }
                Text("Total Price: ${shoppingHistory.totalPrice}", fontSize = 22.sp, fontWeight = FontWeight.Bold)

                if (!shoppingHistory.receiptPath.isNullOrEmpty()) {
                    Image(
                        painter = rememberAsyncImagePainter(File(shoppingHistory.receiptPath)),
                        contentDescription = "Receipt Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Text("No receipt image added.")
                }

            } ?: Text("Loading shopping history...", fontSize = 18.sp)

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Back",
                onClick = {
                    navController.popBackStack()
                },
                backgroundColor = lightBlue
            )

        }
    }


}
