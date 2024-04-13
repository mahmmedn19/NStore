package com.monaser.nstore.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("HomeScreen/{name}")
    data object Details : Screen("DetailsScreen")
}