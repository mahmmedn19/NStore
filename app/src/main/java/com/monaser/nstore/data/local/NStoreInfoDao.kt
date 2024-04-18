package com.monaser.nstore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.monaser.nstore.data.local.entity.ResponseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NStoreInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(responseEntity: ResponseEntity)

    @Query("SELECT * FROM ResponseEntity WHERE id = :id_")
    fun getInfo(id_: String): Flow<ResponseEntity>
    @Update
    suspend fun updateImage(responseEntity: ResponseEntity)

}