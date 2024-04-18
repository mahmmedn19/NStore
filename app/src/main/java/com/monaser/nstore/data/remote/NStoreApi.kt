package com.monaser.nstore.data.remote

import com.monaser.nstore.data.remote.models.ImageData
import com.monaser.nstore.data.remote.models.ResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NStoreApi {

    @GET("images")
    suspend fun getImages(): List<ResponseDto>
    @GET("images/{id}")
    suspend fun getDetailsById(@Path("id")id: String): ResponseDto

    @DELETE("images/{id}")
    suspend fun deleteImageById(id: String)

    @POST("images")
    suspend fun addImage(@Body image: ResponseDto)

}