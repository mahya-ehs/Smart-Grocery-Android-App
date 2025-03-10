package com.example.finalproject.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.finalproject.data.local.entities.GroceryList
import com.example.finalproject.ui.constants.lightGreen

@Composable
fun <T> AnimatedDropdownMenu(
    modifier: Modifier = Modifier,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String,
    placeholder: String = "Select a grocery list"
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(15.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.White)
                .clickable { expanded = !expanded }
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedItem?.let { itemLabel(it) } ?: placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(
            modifier = Modifier
                .padding(top=10.dp)
                .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            ),
            visible = expanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                items.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onItemSelected(item)
                                expanded = false
                            }
                            .padding(12.dp)
                            .background(
                                if (item == selectedItem) Color(0xFFEDE7F6) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = itemLabel(item),
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (item == selectedItem) Color(0xFF512DA8) else Color.Black
                        )
                    }
                }
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun AnimatedDropdownMenuPreview() {
//    val sampleItems = listOf("Apple", "Banana", "Orange", "Mango")
//    var selectedItem by remember { mutableStateOf<String?>(null) }
//
//    Surface(modifier = Modifier.padding(16.dp)) {
//        AnimatedDropdownMenu(
//            items = sampleItems,
//            selectedItem = selectedItem,
//            onItemSelected = { selectedItem = it },
//            itemLabel = { it },
//            placeholder = "Select a fruit"
//        )
//    }
//}
