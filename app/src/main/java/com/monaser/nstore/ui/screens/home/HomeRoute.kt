package com.monaser.nstore.ui.screens.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.monaser.nstore.ui.navigation.Screen

private val HOME_ROUTE = Screen.Home.route

fun NavController.navigateToHome() {
    navigate(HOME_ROUTE)
}

fun NavGraphBuilder.homeRoute() {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}