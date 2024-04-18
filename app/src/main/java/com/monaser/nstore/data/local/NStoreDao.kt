package com.monaser.nstore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monaser.nstore.data.local.entity.ResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NStoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(responseEntity: List<ResponseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(responseEntity: ResponseEntity)

    @Query("SELECT * FROM ResponseEntity ")
    fun getAllImages(): Flow<List<ResponseEntity>>

    @Query("DELETE FROM ResponseEntity WHERE id = :id_")
    suspend fun deleteImageById(id_: String)

    @Query("DELETE FROM ResponseEntity")
    suspend fun deleteAll()

    @Update
    suspend fun updateImage(responseEntity: ResponseEntity)

    @Update
    suspend fun updateAllImages(responseEntities: List<ResponseEntity>)
}
