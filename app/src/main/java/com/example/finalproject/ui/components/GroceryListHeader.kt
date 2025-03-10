package com.example.finalproject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalproject.R

@Composable
fun GroceryListHeader(taskCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.sittingdoodle),
            contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Text(text = "What do you want to buy later?", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold))
        Text(text = "$taskCount products", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))

    }
}
