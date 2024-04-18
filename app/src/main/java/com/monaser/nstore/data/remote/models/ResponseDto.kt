package com.monaser.nstore.data.remote.models


import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("id")
    val id: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
)