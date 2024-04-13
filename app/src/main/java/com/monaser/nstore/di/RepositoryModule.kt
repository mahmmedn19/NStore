package com.monaser.nstore.di

import com.monaser.nstore.data.repository.NStoreRepoImpl
import com.monaser.nstore.domain.repository.NStoreRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNStoreRepo(nStoreRepoImpl: NStoreRepoImpl): NStoreRepo
}