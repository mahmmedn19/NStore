package com.monaser.nstore.data.mappers

import com.monaser.nstore.data.local.entity.ResponseEntity
import com.monaser.nstore.data.remote.models.ResponseDto
import com.monaser.nstore.domain.entity.Response

fun ResponseDto.toEntity(): ResponseEntity {
    return ResponseEntity(
        id = this.id ?: "",
        createdAt = this.createdAt ?: "",
        title = this.title ?: "",
        description = this.description ?: "",
        imageUrl = this.imageUrl ?: ""
    )
}

// dto to domain
fun ResponseDto.toDomain(): Response {
    return Response(
        id = this.id ?: "",
        createdAt = this.createdAt ?: "",
        title = this.title ?: "",
        description = this.description ?: "",
        imageUrl = this.imageUrl ?: ""
    )
}
fun ResponseEntity.toDomain(): Response {
    return Response(
        id = this.id,
        createdAt = this.createdAt,
        title = this.title,
        description = this.description,
        imageUrl = this.imageUrl
    )
}
