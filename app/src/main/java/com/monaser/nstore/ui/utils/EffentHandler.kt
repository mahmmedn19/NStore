package com.monaser.nstore.ui.utils

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@Composable
fun <Effect> EffectHandler(
    effects: Flow<Effect>,
    onEffect: (Effect, NavController) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    LaunchedEffect(key1 = effects) {
        scope.launch {
            effects.collectLatest { effect ->
                onEffect(effect, navController)
            }
        }
    }
}