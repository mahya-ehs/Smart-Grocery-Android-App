package com.example.finalproject.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.ui.components.BottomNavigationBar
import com.example.finalproject.ui.constants.lightGray

@Composable
fun StatisticsScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Oopsie!\nThis page is not built yet...",
                style = MaterialTheme.typography.headlineSmall.copy(color = lightGray),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.groovysittingdoodle),
                contentDescription = null,
//            modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }



        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ){
            BottomNavigationBar(navController, 1)
        }
    }
}