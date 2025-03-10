package com.example.finalproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.finalproject.R
import com.example.finalproject.ui.components.AnimatedDropdownMenu
import com.example.finalproject.ui.components.ShoppingContainer
import com.example.finalproject.ui.components.ShoppingHistoryBottom
import com.example.finalproject.viewmodel.GroceryViewModel
import com.example.finalproject.viewmodel.ShoppingHistoryViewModel
import kotlinx.coroutines.launch

@Composable
fun ShoppingHistoryFlow(navController: NavController, viewModel: ShoppingHistoryViewModel = viewModel()) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> SelectListScreen(pagerState)
                1 -> ProductPricesScreen(pagerState)
                2 -> MapScreen(pagerState)
                3 -> SubmitScreen(navController, pagerState, viewModel)
            }
        }
        val coroutineScope = rememberCoroutineScope()
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (pagerState.currentPage > 0) {
                Button(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                    }
                }) {
                    Text("Back")
                }
            }
            if (pagerState.currentPage < 3) {
                Button(onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }) {
                    Text("Next")
                }
            }
        }

    }
}

@Composable
fun SelectListScreen(
    pagerState: PagerState,
    listsViewModel: GroceryViewModel = viewModel(),
    viewModel: ShoppingHistoryViewModel = viewModel()
) {
    val lists by listsViewModel.allLists.collectAsState(initial = emptyList())
    val availableLists = lists.filter { !it.isBought }

    val selectedListId by viewModel.selectedListId.collectAsState()
    val selectedList = availableLists.find { it.listId == selectedListId }

    val coroutineScope = rememberCoroutineScope()

    ShoppingContainer(
        backgroundImage = R.drawable.blob_haikei,
        content = {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text("Select a Grocery List", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                AnimatedDropdownMenu(
                    items = availableLists,
                    selectedItem = selectedList,
                    onItemSelected = { list ->
                        viewModel.setSelectedListId(list.listId)
                    },
                    itemLabel = { it.listName }
                )

            }
        },
        bottomBar = {
            if (selectedList != null) {
                ShoppingHistoryBottom(pagerState)
            }
        }
    )

}
