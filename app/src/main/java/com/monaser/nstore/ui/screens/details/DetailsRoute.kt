package com.monaser.nstore.ui.screens.details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.monaser.nstore.ui.navigation.Screen

private val DETAILS_ROUTE = Screen.Details.route

fun NavController.navigateToDetails() {
    navigate(DETAILS_ROUTE)
}

fun NavGraphBuilder.detailsRoute() {
    composable(DETAILS_ROUTE) {
        DetailsScreen()
    }
}