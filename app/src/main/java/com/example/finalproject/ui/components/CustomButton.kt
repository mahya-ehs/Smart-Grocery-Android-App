package com.example.finalproject.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject.ui.constants.lightGray
import com.example.finalproject.ui.constants.lightGreen

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    filled: Boolean = true,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textSize: TextUnit = 16.sp,
    icon: ImageVector? = null,
    iconSize: Dp = 20.dp,
    buttonHeight: Dp = 48.dp,
    cornerRadius: Dp = 30.dp
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(cornerRadius)
            )
            .alpha(0.8f)
            .height(buttonHeight)
        ,
        colors = if (filled)
            ButtonDefaults.buttonColors(containerColor = backgroundColor)
        else
            ButtonDefaults.buttonColors(
                containerColor = Color(0xfff6fefc),
                contentColor = backgroundColor
            ),
        border = if (!filled) BorderStroke(1.dp, Color(0xfff6fefc)) else null,
        shape = RoundedCornerShape(cornerRadius)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(iconSize)
            )
        }
        Text(
            text = text,
            fontSize = textSize
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomButton(
            filled = false,
            text = "Add to Cart",
            onClick = {},
            icon = Icons.Default.Add,
            backgroundColor = lightGray,
        )

        CustomButton(
            text = "Delete",
            onClick = {},
            icon = Icons.Default.Delete,
            backgroundColor = lightGreen,
        )
    }
}