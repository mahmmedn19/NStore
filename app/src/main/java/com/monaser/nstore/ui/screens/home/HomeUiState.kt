package com.monaser.nstore.ui.screens.home

data class HomeUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)