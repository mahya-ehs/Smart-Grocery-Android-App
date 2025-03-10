package com.example.finalproject.ui.components

import com.example.finalproject.R

sealed class NavigationItem(val route: String, val icon: Int) {
    object Statistics : NavigationItem("statistics_screen", R.drawable.ic_stats)
    object Profile : NavigationItem("all_shopping_history_screen", R.drawable.ic_bookmark)
    object Home : NavigationItem("main_menu_screen", R.drawable.ic_home)
    object Shopping : NavigationItem("shopping_intro_screen", R.drawable.ic_add)
    object NewList : NavigationItem("all_lists_screen", R.drawable.ic_gro_list)
}
