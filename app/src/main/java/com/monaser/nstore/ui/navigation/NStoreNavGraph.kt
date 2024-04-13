package com.monaser.nstore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.monaser.nstore.ui.screens.details.detailsRoute
import com.monaser.nstore.ui.screens.home.homeRoute

@Composable
fun NStoreNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        homeRoute()
        detailsRoute()
    }
}
