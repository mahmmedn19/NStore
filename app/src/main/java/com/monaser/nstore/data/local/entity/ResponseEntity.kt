package com.monaser.nstore.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "ResponseEntity")
data class ResponseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val createdAt: String,
    val title: String,
    val description: String,
    val imageUrl: String,
)