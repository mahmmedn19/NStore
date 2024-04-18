package com.monaser.nstore.ui.navigation


import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController


val LocalNavigationProvider = compositionLocalOf<NavHostController> {
    error("No NavController found!")
}