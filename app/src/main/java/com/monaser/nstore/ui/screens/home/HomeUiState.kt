package com.monaser.nstore.ui.screens.home

import com.monaser.nstore.domain.entity.Response

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data: List<HomeUiModel> = emptyList()
)

data class HomeUiModel(
    val id: String,
    val createdAt: String,
    val title: String,
    val description: String,
    val imageUrl: String
)

fun Response.toHomeUiModel() = HomeUiModel(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    imageUrl = imageUrl
)