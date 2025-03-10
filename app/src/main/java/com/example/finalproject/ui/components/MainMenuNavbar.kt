package com.example.finalproject.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalproject.ui.constants.darkBlue
import com.example.finalproject.ui.constants.hotPink

@Composable
fun BottomNavigationBar(navController: NavController,
                        selectedIndex: Int = 2) {
    val items = listOf(
        NavigationItem.Profile,
        NavigationItem.Statistics,
        NavigationItem.Home,
        NavigationItem.NewList,
        NavigationItem.Shopping
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(24.dp),
        color = Color.White.copy(alpha = 0.9f),
    ) {
        BottomNavigation(
            backgroundColor = Color.Transparent,
            contentColor = darkBlue,
            elevation = 0.dp
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    selected = index == selectedIndex,
                    onClick = {
                        navController.navigate(item.route)
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(56.dp)
                                .clip(CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                modifier = Modifier.size(if (index == selectedIndex) 46.dp else 24.dp),
                                contentDescription = null,
                                tint = if (index == selectedIndex) darkBlue else hotPink
                            )
                        }
                    },
                    label = null,
                    alwaysShowLabel = false
                )
            }
        }
    }
}
