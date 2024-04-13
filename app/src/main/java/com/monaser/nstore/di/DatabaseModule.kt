package com.monaser.nstore.di

import android.content.Context
import androidx.room.Room
import com.monaser.nstore.data.local.NStoreDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "nstore_database"
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NStoreDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NStoreDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideNStoreDao(database: NStoreDatabase) = database.nStoreDao()
}