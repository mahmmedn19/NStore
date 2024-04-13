package com.monaser.nstore.ui.navigation


import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error("No NavigationProvider provided")
}