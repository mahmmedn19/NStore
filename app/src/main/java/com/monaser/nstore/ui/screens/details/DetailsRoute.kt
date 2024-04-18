package com.monaser.nstore.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.monaser.nstore.ui.navigation.Screen

private val DETAILS_ROUTE = Screen.Details.route

fun NavController.navigateToDetails(itemId: String) {
    navigate("${DETAILS_ROUTE}/{${itemId}}")
}
fun NavGraphBuilder.detailsRoute() {
    composable(
        route = "${DETAILS_ROUTE}/{${DetailsArgs.KEY_ID}}",
        arguments = listOf(
            navArgument(DetailsArgs.KEY_ID) {
                type = NavType.StringType
            }
        )
    ) {
        DetailsScreen()
    }
}

class DetailsArgs(savedStateHandle: SavedStateHandle) {
    val id: String = savedStateHandle[KEY_ID] ?: ""
    companion object {
        const val KEY_ID = "id"
    }
}