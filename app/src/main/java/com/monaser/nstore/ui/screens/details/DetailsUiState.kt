package com.monaser.nstore.ui.screens.details

data class DetailsUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
)