package com.example.finalproject.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.finalproject.ui.constants.hotPink
import com.example.finalproject.ui.constants.lightBlue
import com.example.finalproject.ui.constants.lightGray
import kotlinx.coroutines.launch

@Composable
fun ShoppingHistoryBottom(
    pagerState: PagerState,
    onSubmit: (() -> Unit)? = null
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (pagerState.currentPage >= 0) {
            CustomButton(
                filled = false,
                text = "Back",
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                },
                backgroundColor = lightGray
            )
        }

        if (onSubmit == null && pagerState.currentPage < pagerState.pageCount - 1) {
            CustomButton(
                text = "Next",
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                backgroundColor = lightBlue
            )
        } else if (onSubmit != null) {
            CustomButton(
                text = "Submit",
                onClick = onSubmit,
                backgroundColor = hotPink
            )
        }
    }
}
