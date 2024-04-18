package com.monaser.nstore.data.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.monaser.nstore.data.local.entity.ResponseEntity

@Database(entities = [ResponseEntity::class], version = 1, exportSchema = false)
abstract class NStoreDatabase : RoomDatabase() {
    abstract fun nStoreDao(): NStoreDao
    abstract fun nStoreInfoDao(): NStoreInfoDao
}