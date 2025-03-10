package com.example.finalproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.R
import com.example.finalproject.ui.constants.*
import java.time.format.TextStyle

@Composable
fun GroceryListInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String) -> Unit = {}
) {
    var inputText by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
//                elevation = 1.dp,
                shape = RoundedCornerShape(12.dp),
                elevation = 1.dp,
                ambientColor = lightBlue,
                spotColor = lightBlue
            )
            .alpha(0.8f)
//            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(horizontal = 15.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            placeholder = { Text("Enter list name", color = Color.Gray) },
            modifier = Modifier
                .weight(1f)
                .background(Color.White, RoundedCornerShape(12.dp)),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = lightGray
            ),
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = {
                if (inputText.isNotBlank()) {
                    onAddButtonClick(inputText)
                    inputText = ""
                }
            },
            modifier = Modifier
                .size(44.dp)
                .background(hotPink, shape = CircleShape)
                .alpha(0.8f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_pen),
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroceryListInputBarPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                GroceryListInputBar(
                    onAddButtonClick = { newListName ->
                        println("List Added: $newListName")
                    }
                )
            }
        }
    }
}
