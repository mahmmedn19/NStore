package com.monaser.nstore.domain.repository

import org.mobilenativefoundation.store.store5.MutableStore
import org.mobilenativefoundation.store.store5.Store
import com.monaser.nstore.data.repository.NStoreKey
import com.monaser.nstore.domain.entity.Response
import org.mobilenativefoundation.store.core5.ExperimentalStoreApi

interface NStoreRepo {


    @OptIn(ExperimentalStoreApi::class)
    suspend fun getImageNStore(): MutableStore<NStoreKey, Any>

    suspend fun getImageInfoNStore(): Store<NStoreKey, Any>
}
