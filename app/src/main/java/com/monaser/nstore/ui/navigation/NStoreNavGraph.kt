package com.monaser.nstore.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.monaser.nstore.ui.screens.details.detailsRoute
import com.monaser.nstore.ui.screens.home.homeRoute

@Composable
fun NStoreNavGraph() {
    NavHost(navController = LocalNavigationProvider.current, startDestination = Screen.Home.route) {
        homeRoute()
        detailsRoute()
    }
}
