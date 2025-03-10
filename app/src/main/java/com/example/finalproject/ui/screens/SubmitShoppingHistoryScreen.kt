package com.example.finalproject.ui.screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.ui.components.ShoppingContainer
import com.example.finalproject.ui.components.ShoppingHistoryBottom
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel

@Composable
fun SubmitScreen(navController: NavController, pagerState: PagerState, viewModel: ShoppingHistoryViewModel = viewModel()) {
    val selectedListId by viewModel.selectedListId.collectAsState()
    val selectedStoreName by viewModel.selectedStore.collectAsState()
    val productPrices by viewModel.productPrices.collectAsState()

    ShoppingContainer(
        backgroundImage = R.drawable.green_waves,
        content = {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Review Your Shopping History", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Text("List ID: $selectedListId", fontSize = 18.sp)

                Text("Store: ${selectedStoreName ?: "Not Selected"}", fontSize = 18.sp)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Products & Prices:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                productPrices.forEach { (productId, price) ->
                    Text("• Product ID: $productId -> €${price}", fontSize = 16.sp)
                }
                Image(
                    painter = painterResource(id = R.drawable.layingdoodle),
                    contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        },
        bottomBar = {
            ShoppingHistoryBottom(
                pagerState,
                onSubmit = {
                    viewModel.submitShoppingHistory()
                    Toast.makeText(navController.context, "Shopping History Saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack("main_menu_screen", inclusive = false) // Navigate Back
                }
            )
        }
    )

}
