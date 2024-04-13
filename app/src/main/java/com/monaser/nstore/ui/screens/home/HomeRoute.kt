package com.monaser.nstore.ui.screens.home

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.monaser.nstore.ui.navigation.Screen

private  val HOME_ROUTE = Screen.Home.route

fun NavController.navigateToDetails(name: String) {
    navigate("$HOME_ROUTE/$name")
}

fun NavGraphBuilder.homeRoute() {
    composable(
        route = "$HOME_ROUTE/{${DetailsArgs.KEY_NAME}}",
        arguments = listOf(
            navArgument(DetailsArgs.KEY_NAME) {
                type = NavType.StringType
            }
        )
    ) {
        HomeScreen()
    }
}

class DetailsArgs(savedStateHandle: SavedStateHandle) {
    val name: String = checkNotNull(savedStateHandle["name"])

    companion object {
        const val KEY_NAME = "name"
    }
}