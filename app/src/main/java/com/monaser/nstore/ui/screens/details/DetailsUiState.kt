package com.monaser.nstore.ui.screens.details

import com.monaser.nstore.domain.entity.Response

data class DetailsUiState (
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val data:  DetailsUiModel = DetailsUiModel()
)

data class DetailsUiModel(
    val id: String = "",
    val createdAt: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = ""
)

fun Response.toDetailsUiModel() = DetailsUiModel(
    id = id,
    createdAt = createdAt,
    title = title,
    description = description,
    imageUrl = imageUrl
)