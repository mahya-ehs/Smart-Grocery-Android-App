package com.example.finalproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.screens.AllListsScreen
import com.example.finalproject.ui.screens.AllShoppingHistoryScreen
import com.example.finalproject.ui.screens.GroceryListScreen
import com.example.finalproject.ui.screens.MainMenuScreen
import com.example.finalproject.ui.screens.ProfileScreen
import com.example.finalproject.ui.screens.ShoppingDetailScreen
import com.example.finalproject.ui.screens.ShoppingHistoryFlow
import com.example.finalproject.ui.screens.ShoppingIntroScreen
import com.example.finalproject.ui.screens.StatisticsScreen

@Composable
fun NavigationController() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main_menu_screen",
    ) {
        composable("main_menu_screen") {
            MainMenuScreen(navController = navController)
        }
        composable("all_lists_screen") {
            AllListsScreen(navController = navController)
        }
        composable("grocery_list_screen/{listId}") { backStackEntry ->
            val listId = backStackEntry.arguments?.getString("listId")?.toInt() ?: 0
            GroceryListScreen(navController = navController, listId = listId)
        }
        composable("shopping_intro_screen") {
            ShoppingIntroScreen(navController = navController)
        }
        composable("all_shopping_history_screen") {
            AllShoppingHistoryScreen(navController = navController)
        }
        composable("shopping_history_screen") {
            ShoppingHistoryFlow(navController = navController)
        }
        composable("shopping_detail_screen/{historyId}") { backStackEntry ->
            val historyId = backStackEntry.arguments?.getString("historyId")?.toInt() ?: 0
            ShoppingDetailScreen(navController = navController, historyId = historyId)
        }
        composable("profile_screen") {
            ProfileScreen(navController = navController)
        }
        composable("statistics_screen") {
            StatisticsScreen(navController = navController)
        }
//            composable("submit_shopping_history_screen") {
//                SubmitScreen(navController)
//            }
//            composable("product_prices_screen") {
//                ProductPricesScreen(navController)
//            }
//            composable("map_screen") {
//                MapScreen(navController)
//            }

    }
}
